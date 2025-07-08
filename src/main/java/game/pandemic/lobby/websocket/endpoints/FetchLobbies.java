package game.pandemic.lobby.websocket.endpoints;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.websocket.LobbyWebSocketControllerEndpoint;
import game.pandemic.user.User;
import org.springframework.stereotype.Component;

@Component
public class FetchLobbies extends LobbyWebSocketControllerEndpoint<User> {
    public FetchLobbies(final ObjectMapper objectMapper, final LobbyService lobbyService) {
        super(objectMapper, lobbyService);
    }

    @Override
    public String getEndpointMapping() {
        return "/fetch";
    }

    @Override
    public void consume(final User user, final String message) {
        this.lobbyService.sendLobbiesToUser(user);
    }
}
