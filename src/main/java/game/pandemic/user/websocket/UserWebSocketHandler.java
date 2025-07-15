package game.pandemic.user.websocket;

import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;
import game.pandemic.user.User;
import game.pandemic.validation.ValidationService;
import game.pandemic.websocket.WebSocketSessionRegistry;
import game.pandemic.websocket.auth.AccessTokenService;
import game.pandemic.websocket.endpoint.IWebSocketController;
import game.pandemic.websocket.endpoint.WebSocketHandler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class UserWebSocketHandler extends WebSocketHandler<User> {
    public UserWebSocketHandler(final WebSocketSessionRegistry<User> webSocketSessionRegistry,
                                final IUnicastAndMulticastMessenger<WebSocketSession> webSocketSessionMessenger,
                                final AccessTokenService<User> accessTokenService,
                                final List<IWebSocketController<User>> webSocketControllers,
                                final ObjectMapper objectMapper,
                                final ValidationService validationService) {
        super(
                webSocketSessionRegistry,
                webSocketSessionMessenger,
                accessTokenService,
                webSocketControllers,
                objectMapper,
                validationService
        );
    }

    @Override
    public String getEndpointMapping() {
        return "/user";
    }

    @Override
    protected void handleAuthenticationSuccess(final WebSocketSession session, final User user) {
        super.handleAuthenticationSuccess(session, user);
        sendMessageWithAllUsersToAllUsers();
    }

    @Override
    public void afterConnectionClosed(@NonNull final WebSocketSession session, @NonNull final CloseStatus status) {
        super.afterConnectionClosed(session, status);
        if (!CloseStatus.GOING_AWAY.equalsCode(status)) {
            sendMessageWithAllUsersToAllUsers();
        }
    }

    protected void sendMessageWithAllUsersToAllUsers() {
        this.webSocketSessionMessenger.multicast(
                this.webSocketSessionRegistry.findAllSessions(),
                this.webSocketSessionRegistry.findAllAuthenticationObjects(),
                JacksonView.Read.class
        );
    }
}
