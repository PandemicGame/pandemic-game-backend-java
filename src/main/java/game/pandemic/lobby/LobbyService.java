package game.pandemic.lobby;

import game.pandemic.game.GameOptions;
import game.pandemic.game.GameService;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.events.CreateLobbyEvent;
import game.pandemic.lobby.events.JoinLobbyEvent;
import game.pandemic.lobby.events.LeaveLobbyEvent;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.lobby.member.LobbyMemberRepository;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.member.messengers.ILobbyMemberMessenger;
import game.pandemic.lobby.websocket.LobbyAndAccessTokenHolder;
import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;
import game.pandemic.user.User;
import game.pandemic.websocket.auth.AccessTokenService;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    private final GameService gameService;

    @PreDestroy
    private void onShutdown() {
        this.lobbyRepository.closeAll();
    }

    @Transactional
    public List<Lobby> getAllLobbies() {
        return this.lobbyRepository.findAllByIsClosedFalse();
    }

    @Transactional
    public Optional<LobbyAndAccessTokenHolder> joinLobby(final User user, final String lobbyId) {
        return findLobbyAndExecute(lobbyId, lobby -> addUserToLobbyAndProceed(
                user,
                m -> addMemberToLobby(lobby, m),
                this::sendLobbyToMembers
        ));
    }

    private <R> Optional<R> findLobbyAndExecute(final String lobbyId, final Function<Lobby, R> callback) {
        if (NumberUtils.isCreatable(lobbyId)) {
            final Long id = Long.parseLong(lobbyId);
            final Optional<Lobby> lobbyOptional = this.lobbyRepository.findByIdAndIsClosedFalse(id);
            if (lobbyOptional.isPresent()) {
                return Optional.of(callback.apply(lobbyOptional.get()));
            }
        }
        return Optional.empty();
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
    public LobbyAndAccessTokenHolder createLobby(final User user, final String name) {
        return addUserToLobbyAndProceed(
                user,
                m -> new CreateLobbyEvent(name, m, this.gameService.createDefaultGameOptions()).createLobby(),
                l -> log.info("Created lobby with name: " + name)
        );
    }

    private LobbyAndAccessTokenHolder addUserToLobbyAndProceed(final User user,
                                          final Function<UserLobbyMember, Lobby> memberToLobbyFunction,
                                          final Consumer<Lobby> callback) {
        final UserLobbyMember userLobbyMember = createUserLobbyMember(user);
        final Lobby lobby = memberToLobbyFunction.apply(userLobbyMember);
        final Lobby saved = this.lobbyRepository.save(lobby);
        callback.accept(saved);
        return createLobbyAndAccessTokenHolder(userLobbyMember, saved);
    }

    private UserLobbyMember createUserLobbyMember(final User user) {
        final UserLobbyMember userLobbyMember = new UserLobbyMember(user);
        return this.lobbyMemberRepository.save(userLobbyMember);
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
        this.lobbyRepository.findLobbyByMembersContaining(userLobbyMember).ifPresent(
                lobby -> executeIfLobbyMemberIsOwner(
                        lobby,
                        userLobbyMember,
                        "start game",
                        this::startGame
                )
        );
    }

    private void startGame(final Lobby lobby) {
        if (lobby.getMembers().size() < 2) {
            log.warn("Cannot start game in lobby \"" + lobby.getName() + "\" as there are not enough members.");
            return;
        }

        this.gameService.startGameInLobby(lobby, l -> {
            final Lobby saved = this.lobbyRepository.save(l);

            log.info("A game was started in lobby \"" + saved.getName() + "\".");
        });
    }

    private void executeIfLobbyMemberIsOwner(final Lobby lobby, final LobbyMember lobbyMember, final String actionName, final Consumer<Lobby> callback) {
        if (!lobby.isOwner(lobbyMember)) {
            log.warn("Non-owner \"" + lobbyMember.getName() + "\" cannot execute the following action in lobby \"" + lobby.getName() + "\": " + actionName);
            return;
        }

        callback.accept(lobby);
    }

    @Transactional
    public void updateOptions(final UserLobbyMember userLobbyMember, final GameOptions gameOptions) {
        this.lobbyRepository.findLobbyByMembersContaining(userLobbyMember).ifPresent(
                lobby -> executeIfLobbyMemberIsOwner(
                        lobby,
                        userLobbyMember,
                        "update options",
                        l -> updateOptions(l, gameOptions)
                )
        );
    }

    private void updateOptions(final Lobby lobby, final GameOptions gameOptions) {
        this.gameService.addChoicesToGameOptions(gameOptions);

        lobby.setGameOptions(gameOptions);

        final Lobby saved = this.lobbyRepository.save(lobby);

        sendLobbyToMembers(saved);
    }
}
