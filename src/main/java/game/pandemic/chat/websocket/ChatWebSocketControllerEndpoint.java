package game.pandemic.chat.websocket;

import game.pandemic.chat.ChatMessageSender;
import game.pandemic.chat.ChatService;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.endpoint.IWebSocketEndpointConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChatWebSocketControllerEndpoint<A extends ChatMessageSender & IWebSocketAuthenticationObject> implements IChatWebSocketControllerEndpoint<A>, IWebSocketEndpointConsumer<A> {
    protected final ObjectMapper objectMapper;
    protected final ChatService chatService;
}
