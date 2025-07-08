package game.pandemic.lobby.websocket.endpoints;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.websocket.LobbyWebSocketControllerEndpoint;
import game.pandemic.user.User;
import org.springframework.stereotype.Component;

@Component
public class CreateLobby extends LobbyWebSocketControllerEndpoint<User> {
    public CreateLobby(final ObjectMapper objectMapper, final LobbyService lobbyService) {
        super(objectMapper, lobbyService);
    }

    @Override
    public String getEndpointMapping() {
        return "/create";
    }

    @Override
    public void consume(final User user, final String message) {
        this.lobbyService.createLobby(user, message);
    }
}
