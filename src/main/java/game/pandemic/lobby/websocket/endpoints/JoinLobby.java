package game.pandemic.lobby.websocket.endpoints;

import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.websocket.LobbyWebSocketControllerEndpoint;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import game.pandemic.user.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class JoinLobby extends LobbyWebSocketControllerEndpoint<User> {
    public JoinLobby(final ObjectMapper objectMapper, final LobbyService lobbyService, final IUnicastMessenger<WebSocketSession> webSocketSessionMessenger) {
        super(objectMapper, lobbyService, webSocketSessionMessenger);
    }

    @Override
    public String getEndpointMapping() {
        return "/join";
    }

    @Override
    public void consume(final WebSocketSession session, final User user, final String message) {
        this.lobbyService.joinLobby(user, message).ifPresent(
                tokenHolder -> this.webSocketSessionMessenger.unicast(
                        session,
                        tokenHolder,
                        JacksonView.Read.class
                )
        );
    }
}
