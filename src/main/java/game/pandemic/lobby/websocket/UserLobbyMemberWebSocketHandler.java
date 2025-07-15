package game.pandemic.lobby.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.validation.ValidationService;
import game.pandemic.websocket.WebSocketMessenger;
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
public class UserLobbyMemberWebSocketHandler extends WebSocketHandler<UserLobbyMember> {
    private final LobbyService lobbyService;

    public UserLobbyMemberWebSocketHandler(final WebSocketSessionRegistry<UserLobbyMember> webSocketSessionRegistry,
                                           final WebSocketMessenger webSocketSessionMessenger,
                                           final AccessTokenService<UserLobbyMember> accessTokenService,
                                           final List<IWebSocketController<UserLobbyMember>> iWebSocketControllers,
                                           final ObjectMapper objectMapper,
                                           final ValidationService validationService,
                                           final LobbyService lobbyService) {
        super(
                webSocketSessionRegistry,
                webSocketSessionMessenger,
                accessTokenService,
                iWebSocketControllers,
                objectMapper,
                validationService
        );
        this.lobbyService = lobbyService;
    }

    @Override
    public String getEndpointMapping() {
        return "/lobby";
    }

    @Override
    public void afterConnectionClosed(@NonNull final WebSocketSession session, @NonNull final CloseStatus status) {
        if (!SHUTDOWN_CLOSE_STATUS.equalsCode(status)) {
            this.webSocketSessionRegistry.findAuthenticationObjectForSession(session).ifPresent(this.lobbyService::leaveLobby);
        }
        super.afterConnectionClosed(session, status);
    }
}
