package game.pandemic.lobby;

import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.events.CreateLobbyEvent;
import game.pandemic.lobby.events.JoinLobbyEvent;
import game.pandemic.lobby.events.LeaveLobbyEvent;
import game.pandemic.lobby.events.StartGameLobbyEvent;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.lobby.member.LobbyMemberRepository;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.member.messengers.ILobbyMemberMessenger;
import game.pandemic.lobby.websocket.LobbyAndAccessTokenHolder;
import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;
import game.pandemic.user.User;
import game.pandemic.websocket.auth.AccessTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class LobbyService {
    private final AccessTokenService<UserLobbyMember> accessTokenService;
    private final LobbyRepository lobbyRepository;
    private final LobbyMemberRepository lobbyMemberRepository;
    private final IGeneralPurposeMessenger<User> userMessenger;
    private final ILobbyMemberMessenger<LobbyMember> lobbyMemberMessenger;

    @Transactional
    public void sendLobbiesToUser(final User user) {
        this.userMessenger.unicast(user, this.lobbyRepository.findAllByIsClosedFalse(), JacksonView.Read.class);
    }

    @Transactional
    public void joinLobby(final User user, final String lobbyId) {
        findLobbyAndExecute(lobbyId, lobby -> addUserToLobbyAndProceed(
                user,
                m -> addMemberToLobby(lobby, m),
                this::sendLobbyToMembers
        ));
    }

    private void findLobbyAndExecute(final String lobbyId, final Consumer<Lobby> callback) {
        if (NumberUtils.isCreatable(lobbyId)) {
            final Long id = Long.parseLong(lobbyId);
            this.lobbyRepository.findByIdAndIsClosedFalse(id).ifPresent(callback);
        }
    }

    private Lobby addMemberToLobby(final Lobby lobby, final LobbyMember member) {
        lobby.processEvent(new JoinLobbyEvent(member));
        log.info("LobbyMember \"" + member.getName() + "\" joined the lobby \"" + lobby.getName() + "\".");
        return lobby;
    }

    private void sendLobbyToMembers(final Lobby lobby) {
        this.lobbyMemberMessenger.multicast(
                lobby.getMembers(),
                lobby,
                JacksonView.Read.class
        );
    }

    @Transactional
    public void createLobby(final User user, final String name) {
        addUserToLobbyAndProceed(
                user,
                m -> new CreateLobbyEvent(name, m).createLobby(),
                l -> log.info("Created lobby with name: " + name)
        );
    }

    private void addUserToLobbyAndProceed(final User user,
                                          final Function<UserLobbyMember, Lobby> memberToLobbyFunction,
                                          final Consumer<Lobby> callback) {
        final UserLobbyMember userLobbyMember = createUserLobbyMember(user);
        final Lobby lobby = memberToLobbyFunction.apply(userLobbyMember);
        final Lobby saved = this.lobbyRepository.save(lobby);
        sendLobbyAndAccessTokenHolderToUserLobbyMember(userLobbyMember, saved);
        callback.accept(lobby);
    }

    private UserLobbyMember createUserLobbyMember(final User user) {
        final UserLobbyMember userLobbyMember = new UserLobbyMember(user);
        return this.lobbyMemberRepository.save(userLobbyMember);
    }

    private void sendLobbyAndAccessTokenHolderToUserLobbyMember(final UserLobbyMember userLobbyMember,
                                                                final Lobby lobby) {
        this.userMessenger.unicast(
                userLobbyMember.getUser(),
                createLobbyAndAccessTokenHolder(userLobbyMember, lobby),
                JacksonView.Read.class
        );
    }

    private LobbyAndAccessTokenHolder createLobbyAndAccessTokenHolder(final UserLobbyMember userLobbyMember,
                                                                      final Lobby lobby) {
        return new LobbyAndAccessTokenHolder(
                lobby,
                this.accessTokenService.createAccessTokenForObject(userLobbyMember),
                userLobbyMember
        );
    }

    @Transactional
    public void leaveLobby(final UserLobbyMember userLobbyMember) {
        this.lobbyRepository.findLobbyByMembersContaining(userLobbyMember).ifPresent(lobby -> leaveLobby(lobby, userLobbyMember));
    }

    private void leaveLobby(final Lobby lobby, final LobbyMember lobbyMember) {
        final boolean isLobbyClosedBeforeLeave = lobby.isClosed();

        lobby.processEvent(new LeaveLobbyEvent(lobbyMember));

        this.lobbyMemberRepository.detachLobby(lobbyMember.getId());

        final Lobby saved = this.lobbyRepository.save(lobby);

        log.info("LobbyMember \"" + lobbyMember.getName() + "\" left lobby \"" + saved.getName() + "\".");

        if (!isLobbyClosedBeforeLeave && lobby.isClosed()) {
            handleLobbyClosure(lobby);
        }

        if (!lobby.isClosed()) {
            sendLobbyToMembers(saved);
        }
    }

    private void handleLobbyClosure(final Lobby lobby) {
        if (lobby.isClosed()) {
            this.lobbyMemberMessenger.closeConnection(lobby.getMembers());
            this.userMessenger.broadcast(this.lobbyRepository.findAllByIsClosedFalse(), JacksonView.Read.class);
        }
    }

    @Transactional
    public void startGame(final UserLobbyMember userLobbyMember) {
        this.lobbyRepository.findLobbyByMembersContaining(userLobbyMember).ifPresent(lobby -> startGame(lobby, userLobbyMember));
    }

    private void startGame(final Lobby lobby, final UserLobbyMember userLobbyMember) {
        if (!lobby.isOwner(userLobbyMember)) {
            log.warn("Non-owner \"" + userLobbyMember.getName() + "\" cannot start a game in lobby \"" + lobby.getName() + "\".");
            return;
        }

        if (lobby.getMembers().size() < 2) {
            log.warn("Cannot start game in lobby \"" + lobby.getName() + "\" as there are not enough members.");
            return;
        }

        lobby.processEvent(new StartGameLobbyEvent());

        final Lobby saved = this.lobbyRepository.save(lobby);

        log.info("A game was started in lobby \"" + saved.getName() + "\".");

        this.lobbyMemberMessenger.multicast(
                saved.getMembers(),
                saved.getGame(),
                JacksonView.Read.class
        );
    }
}
