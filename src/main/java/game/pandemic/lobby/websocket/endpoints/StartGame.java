package game.pandemic.lobby.websocket.endpoints;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.websocket.LobbyWebSocketControllerEndpoint;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class StartGame extends LobbyWebSocketControllerEndpoint<UserLobbyMember> {
    public StartGame(final ObjectMapper objectMapper, final LobbyService lobbyService, final IUnicastMessenger<WebSocketSession> webSocketSessionMessenger) {
        super(objectMapper, lobbyService, webSocketSessionMessenger);
    }

    @Override
    public String getEndpointMapping() {
        return "/start-game";
    }

    @Override
    public void consume(final WebSocketSession session, final UserLobbyMember userLobbyMember, final String message) {
        this.lobbyService.startGame(userLobbyMember);
    }
}
