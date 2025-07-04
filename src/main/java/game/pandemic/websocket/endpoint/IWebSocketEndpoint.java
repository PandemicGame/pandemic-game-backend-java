package game.pandemic.websocket.endpoint;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;

public sealed interface IWebSocketEndpoint<A extends IWebSocketAuthenticationObject> permits IWebSocketControllerEndpoint, IWebSocketEndpointConsumer, IWebSocketEndpointDelegator {
    String getEndpointMapping();
}
