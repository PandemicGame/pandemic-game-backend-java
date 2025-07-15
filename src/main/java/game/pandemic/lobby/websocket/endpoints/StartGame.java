package game.pandemic.lobby.websocket.endpoints;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.websocket.LobbyWebSocketControllerEndpoint;
import org.springframework.stereotype.Component;

@Component
public class StartGame extends LobbyWebSocketControllerEndpoint<UserLobbyMember> {
    public StartGame(final ObjectMapper objectMapper, final LobbyService lobbyService) {
        super(objectMapper, lobbyService);
    }

    @Override
    public String getEndpointMapping() {
        return "/start-game";
    }

    @Override
    public void consume(final UserLobbyMember userLobbyMember, final String message) {
        this.lobbyService.startGame(userLobbyMember);
    }
}
