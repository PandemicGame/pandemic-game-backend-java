package game.pandemic.user.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.user.User;
import game.pandemic.websocket.WebSocketMessenger;
import game.pandemic.websocket.WebSocketSessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserWebSocketMessenger extends WebSocketMessenger<User> {
    public UserWebSocketMessenger(final WebSocketSessionRegistry<User> webSocketSessionRegistry, final ObjectMapper objectMapper) {
        super(webSocketSessionRegistry, objectMapper);
    }
}
