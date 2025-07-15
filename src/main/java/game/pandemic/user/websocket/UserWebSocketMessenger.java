package game.pandemic.user.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.persistent.IMulticastPersistentConnectionMessenger;
import game.pandemic.user.User;
import game.pandemic.websocket.WebSocketSessionRegistry;
import game.pandemic.websocket.auth.WebSocketAuthenticationObjectMessenger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class UserWebSocketMessenger extends WebSocketAuthenticationObjectMessenger<User> {
    public UserWebSocketMessenger(final WebSocketSessionRegistry<User> webSocketSessionRegistry,
                                  final IMulticastPersistentConnectionMessenger<WebSocketSession> webSocketMessenger,
                                  final ObjectMapper objectMapper) {
        super(webSocketSessionRegistry, webSocketMessenger, objectMapper);
    }
}
