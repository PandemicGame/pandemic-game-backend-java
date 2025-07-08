package game.pandemic.lobby;

import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.member.LobbyMemberRepository;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.websocket.LobbyAndAccessTokenHolder;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import game.pandemic.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class LobbyService {
    private final LobbyRepository lobbyRepository;
    private final LobbyMemberRepository lobbyMemberRepository;
    private final IUnicastMessenger<User> userMessenger;

    @Transactional
    public void createLobby(final User user, final String name) {
        final UserLobbyMember userLobbyMember = createUserLobbyMember(user);
        final Lobby lobby = new Lobby(name, userLobbyMember);
        final Lobby saved = this.lobbyRepository.save(lobby);
        log.info("Created lobby with name: " + name);
        this.userMessenger.unicast(user, createLobbyAndAccessTokenHolder(userLobbyMember, saved), JacksonView.Read.class);
    }

    private LobbyAndAccessTokenHolder createLobbyAndAccessTokenHolder(final UserLobbyMember userLobbyMember, final Lobby lobby) {
        return new LobbyAndAccessTokenHolder(lobby, userLobbyMember.getAccessToken());
    }

    private UserLobbyMember createUserLobbyMember(final User user) {
        final UserLobbyMember userLobbyMember = new UserLobbyMember(user);
        userLobbyMember.setAccessToken(UUID.randomUUID());
        return this.lobbyMemberRepository.save(userLobbyMember);
    }
}
