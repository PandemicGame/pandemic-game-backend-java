package game.pandemic.websocket.endpoint;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;

public non-sealed interface IWebSocketEndpointConsumer<A extends IWebSocketAuthenticationObject> extends IWebSocketEndpoint<A> {
    void consume(final A authenticationObject, final String message);
}
