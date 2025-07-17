package game.pandemic.chat.websocket.endpoints;

import game.pandemic.chat.ChatMessageSender;
import game.pandemic.chat.ChatService;
import game.pandemic.chat.websocket.ChatWebSocketControllerEndpoint;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class FetchChat<A extends ChatMessageSender & IWebSocketAuthenticationObject> extends ChatWebSocketControllerEndpoint<A> {
    public FetchChat(final ObjectMapper objectMapper, final ChatService chatService) {
        super(objectMapper, chatService);
    }

    @Override
    public String getEndpointMapping() {
        return "/fetch";
    }

    @Override
    public void consume(final WebSocketSession session, final A chatMember, final String chatId) {
        this.chatService.sendChatWithIdToChatMember(chatMember, chatId);
    }
}
