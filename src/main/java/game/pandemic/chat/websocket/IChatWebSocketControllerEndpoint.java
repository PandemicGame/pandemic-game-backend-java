package game.pandemic.chat.websocket;

import game.pandemic.chat.ChatMessageSender;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.endpoint.IWebSocketControllerEndpoint;

public interface IChatWebSocketControllerEndpoint<A extends ChatMessageSender & IWebSocketAuthenticationObject> extends IWebSocketControllerEndpoint<A> {
}
