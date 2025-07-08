package game.pandemic.user.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import game.pandemic.user.User;
import game.pandemic.websocket.WebSocketSessionRegistry;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import game.pandemic.websocket.endpoint.IWebSocketController;
import game.pandemic.websocket.endpoint.WebSocketHandler;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class UserWebSocketHandler extends WebSocketHandler<User> {
    public UserWebSocketHandler(final WebSocketSessionRegistry<User> webSocketSessionRegistry,
                                final IUnicastMessenger<WebSocketSession> webSocketSessionUnicastMessenger,
                                final IWebSocketAuthenticationObjectRepository<User> webSocketAuthenticationObjectRepository,
                                final List<IWebSocketController<User>> webSocketControllers,
                                final ObjectMapper objectMapper,
                                final Validator validator) {
        super(
                webSocketSessionRegistry,
                webSocketSessionUnicastMessenger,
                webSocketAuthenticationObjectRepository,
                webSocketControllers,
                objectMapper,
                validator
        );
    }

    @Override
    public String getEndpointMapping() {
        return "/user";
    }
}
