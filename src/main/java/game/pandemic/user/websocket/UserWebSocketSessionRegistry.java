package game.pandemic.user.websocket;

import game.pandemic.user.User;
import game.pandemic.websocket.WebSocketSessionRegistry;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import org.springframework.stereotype.Component;

@Component
public class UserWebSocketSessionRegistry extends WebSocketSessionRegistry<User> {
    public UserWebSocketSessionRegistry(final IWebSocketAuthenticationObjectRepository<User> webSocketAuthenticationObjectRepository) {
        super(webSocketAuthenticationObjectRepository);
    }
}
