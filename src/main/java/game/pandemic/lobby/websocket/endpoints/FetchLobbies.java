package game.pandemic.lobby.websocket.endpoints;

import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.Lobby;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.websocket.LobbyWebSocketControllerEndpoint;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import game.pandemic.user.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class FetchLobbies extends LobbyWebSocketControllerEndpoint<User> {
    public FetchLobbies(final ObjectMapper objectMapper, final LobbyService lobbyService, final IUnicastMessenger<WebSocketSession> webSocketSessionMessenger) {
        super(objectMapper, lobbyService, webSocketSessionMessenger);
    }

    @Override
    public String getEndpointMapping() {
        return "/fetch";
    }

    @Override
    @Transactional
    public void consume(final WebSocketSession session, final User user, final String message) {
        final List<Lobby> lobbies = this.lobbyService.getAllLobbies();
        this.webSocketSessionMessenger.unicast(session, lobbies, JacksonView.Read.class);
    }
}
