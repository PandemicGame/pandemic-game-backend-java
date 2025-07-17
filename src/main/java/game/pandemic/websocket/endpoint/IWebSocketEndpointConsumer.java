package game.pandemic.websocket.endpoint;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import org.springframework.web.socket.WebSocketSession;

public non-sealed interface IWebSocketEndpointConsumer<A extends IWebSocketAuthenticationObject> extends IWebSocketEndpoint<A> {
    void consume(final WebSocketSession session, final A authenticationObject, final String message);
}
