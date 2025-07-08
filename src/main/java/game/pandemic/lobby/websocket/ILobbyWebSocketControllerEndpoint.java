package game.pandemic.lobby.websocket;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.endpoint.IWebSocketControllerEndpoint;

public interface ILobbyWebSocketControllerEndpoint<A extends IWebSocketAuthenticationObject> extends IWebSocketControllerEndpoint<A> {
}
