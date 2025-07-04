package game.pandemic.websocket.endpoint;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;

public interface IWebSocketController<A extends IWebSocketAuthenticationObject> extends IWebSocketEndpointDelegator<A, IWebSocketControllerEndpoint<A>> {
}
