package game.pandemic.lobby.websocket.endpoints;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.websocket.LobbyWebSocketControllerEndpoint;
import game.pandemic.user.User;
import org.springframework.stereotype.Component;

@Component
public class JoinLobby extends LobbyWebSocketControllerEndpoint<User> {
    public JoinLobby(final ObjectMapper objectMapper, final LobbyService lobbyService) {
        super(objectMapper, lobbyService);
    }

    @Override
    public String getEndpointMapping() {
        return "/join";
    }

    @Override
    public void consume(final User user, final String message) {
        this.lobbyService.joinLobby(user, message);
    }
}
