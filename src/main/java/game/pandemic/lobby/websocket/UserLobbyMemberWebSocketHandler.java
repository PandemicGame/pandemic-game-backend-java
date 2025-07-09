package game.pandemic.lobby.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;
import game.pandemic.validation.ValidationService;
import game.pandemic.websocket.WebSocketSessionRegistry;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import game.pandemic.websocket.endpoint.IWebSocketController;
import game.pandemic.websocket.endpoint.WebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class UserLobbyMemberWebSocketHandler extends WebSocketHandler<UserLobbyMember> {
    public UserLobbyMemberWebSocketHandler(final WebSocketSessionRegistry<UserLobbyMember> webSocketSessionRegistry,
                                           final IUnicastAndMulticastMessenger<WebSocketSession> webSocketSessionMessenger,
                                           final IWebSocketAuthenticationObjectRepository<UserLobbyMember> webSocketAuthenticationObjectRepository,
                                           final List<IWebSocketController<UserLobbyMember>> iWebSocketControllers,
                                           final ObjectMapper objectMapper,
                                           final ValidationService validationService) {
        super(
                webSocketSessionRegistry,
                webSocketSessionMessenger,
                webSocketAuthenticationObjectRepository,
                iWebSocketControllers,
                objectMapper,
                validationService
        );
    }

    @Override
    public String getEndpointMapping() {
        return "/lobby";
    }
}
