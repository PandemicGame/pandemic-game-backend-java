package game.pandemic.lobby.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.endpoint.IWebSocketEndpointConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LobbyWebSocketControllerEndpoint<A extends IWebSocketAuthenticationObject> implements ILobbyWebSocketControllerEndpoint<A>, IWebSocketEndpointConsumer<A> {
    protected final ObjectMapper objectMapper;
    protected final LobbyService lobbyService;
    protected final IUnicastMessenger<WebSocketSession> webSocketSessionMessenger;
}
