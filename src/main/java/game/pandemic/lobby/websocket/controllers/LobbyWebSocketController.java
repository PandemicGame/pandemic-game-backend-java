package game.pandemic.lobby.websocket.controllers;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.endpoint.IWebSocketControllerEndpoint;
import game.pandemic.websocket.endpoint.WebSocketController;

import java.util.List;

public abstract class LobbyWebSocketController<A extends IWebSocketAuthenticationObject> extends WebSocketController<A> {
    protected LobbyWebSocketController(final List<IWebSocketControllerEndpoint<A>> webSocketControllerEndpoints) {
        super(webSocketControllerEndpoints);
    }

    @Override
    public String getEndpointMapping() {
        return "/lobby";
    }
}
