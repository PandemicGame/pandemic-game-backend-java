package game.pandemic.user.websocket;

import game.pandemic.user.User;
import game.pandemic.websocket.WebSocketSessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserWebSocketSessionRegistry extends WebSocketSessionRegistry<User> {
}
