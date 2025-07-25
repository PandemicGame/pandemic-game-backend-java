package game.pandemic.chat.websocket.endpoints;

import game.pandemic.chat.ChatMessageSender;
import game.pandemic.chat.ChatService;
import game.pandemic.chat.message.ChatMessage;
import game.pandemic.chat.websocket.ChatWebSocketControllerEndpoint;
import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SendChatMessage<A extends ChatMessageSender & IWebSocketAuthenticationObject> extends ChatWebSocketControllerEndpoint<A> {
    public SendChatMessage(final ObjectMapper objectMapper, final ChatService chatService) {
        super(objectMapper, chatService);
    }

    @Override
    public String getEndpointMapping() {
        return "/send";
    }

    @Override
    public void consume(final WebSocketSession session, final A chatMessageSender, final String message) {
        this.objectMapper.deserialize(
                message,
                ChatMessage.class,
                JacksonView.Create.class,
                c -> this.chatService.createChatMessage(chatMessageSender, c)
        );
    }
}
