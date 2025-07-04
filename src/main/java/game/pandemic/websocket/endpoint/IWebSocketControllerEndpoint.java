package game.pandemic.websocket.endpoint;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;

public non-sealed interface IWebSocketControllerEndpoint<A extends IWebSocketAuthenticationObject> extends IWebSocketEndpoint<A> {
}
