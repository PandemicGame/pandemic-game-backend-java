package game.pandemic.chat.websocket.controllers;

import game.pandemic.chat.ChatMessageSender;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.endpoint.IWebSocketControllerEndpoint;
import game.pandemic.websocket.endpoint.WebSocketController;

import java.util.List;

public abstract class ChatWebSocketController<A extends ChatMessageSender & IWebSocketAuthenticationObject> extends WebSocketController<A> {
    protected ChatWebSocketController(final List<IWebSocketControllerEndpoint<A>> webSocketControllerEndpoints) {
        super(webSocketControllerEndpoints);
    }

    @Override
    public String getEndpointMapping() {
        return "/chat";
    }
}
