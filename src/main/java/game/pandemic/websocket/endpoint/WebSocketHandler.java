package game.pandemic.websocket.endpoint;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;
import game.pandemic.validation.ValidationService;
import game.pandemic.websocket.WebSocketSessionRegistry;
import game.pandemic.websocket.auth.AccessTokenService;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.message.WebSocketMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Log4j2
public abstract class WebSocketHandler<A extends IWebSocketAuthenticationObject> extends TextWebSocketHandler implements IWebSocketEndpointDelegator<A, IWebSocketController<A>> {
    public static final String AUTH_SUCCESS_RESPONSE = "AUTH_SUCCESS";

    protected final WebSocketSessionRegistry<A> webSocketSessionRegistry;
    protected final IUnicastAndMulticastMessenger<WebSocketSession> webSocketSessionMessenger;
    protected final AccessTokenService<A> accessTokenService;
    protected final List<IWebSocketController<A>> webSocketControllers;
    protected final ObjectMapper objectMapper;
    protected final ValidationService validationService;

    @Override
    public List<IWebSocketController<A>> getAllEndpoints() {
        return this.webSocketControllers;
    }

    @Override
    public void noValidEndpointsForPathHandler(final String path) {
        log.warn("There is no endpoint at path: \"" + path + "\"");
    }

    @Override
    public void afterConnectionEstablished(@NonNull final WebSocketSession session) {
        log.info("New WebSocket connection at endpoint \"" + getEndpointMapping() + "\": " + session.getId());
    }

    @Override
    protected void handleTextMessage(@NonNull final WebSocketSession session, @NonNull final TextMessage message) {
        deserializeTextMessageAndProcess(session, message);
    }

    protected void deserializeTextMessageAndProcess(final WebSocketSession session, final TextMessage message) {
        final String payload = message.getPayload();
        this.objectMapper.deserialize(payload, WebSocketMessage.class, w -> validateWebSocketMessageAndProcess(session, w));
    }

    protected void validateWebSocketMessageAndProcess(final WebSocketSession session, final WebSocketMessage webSocketMessage) {
        this.validationService.validate(webSocketMessage, w -> delegateWebSocketMessageToHandlers(session, w));
    }

    protected void delegateWebSocketMessageToHandlers(final WebSocketSession session, final WebSocketMessage webSocketMessage) {
        switch (webSocketMessage.getMessageType()) {
            case AUTH -> handleAuthentication(session, webSocketMessage);
            case MESSAGE -> handleMessage(session, webSocketMessage);
        }
    }

    protected void handleAuthentication(final WebSocketSession session, final WebSocketMessage webSocketMessage) {
        final String accessToken = webSocketMessage.getPayload();
        this.accessTokenService.parseAccessTokenToObject(accessToken).ifPresentOrElse(
                a -> handleAuthenticationSuccess(session, a),
                () -> handleAuthenticationError(session)
        );
    }

    protected void handleAuthenticationSuccess(final WebSocketSession session, final A authenticationObject) {
        this.webSocketSessionRegistry.removeSession(session);
        this.webSocketSessionRegistry.addSession(session, authenticationObject);
        this.webSocketSessionMessenger.unicast(session, AUTH_SUCCESS_RESPONSE);
    }

    protected void handleAuthenticationError(final WebSocketSession session) {
        log.warn("There is no authentication object available for given access token for connection: " + session.getId());
    }

    protected void handleMessage(final WebSocketSession session, final WebSocketMessage webSocketMessage) {
        this.webSocketSessionRegistry.findAuthenticationObjectForSession(session).ifPresentOrElse(
                a -> handleMessageWithAuthentication(session, webSocketMessage, a),
                () -> handleMessageWithoutAuthentication(session, webSocketMessage)
        );
    }

    protected void handleMessageWithAuthentication(final WebSocketSession session, final WebSocketMessage webSocketMessage, final A authenticationObject) {
        delegateToEndpoints(webSocketMessage.getDestination(), authenticationObject, webSocketMessage.getPayload());
    }

    protected void handleMessageWithoutAuthentication(final WebSocketSession session, final WebSocketMessage webSocketMessage) {
        log.info("WebSocketMessage received over unauthenticated connection: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(@NonNull final WebSocketSession session, @NonNull final CloseStatus status) {
        log.info("WebSocket connection closed at endpoint \"" + getEndpointMapping() + "\": " + session.getId());
        this.webSocketSessionRegistry.removeSession(session);
    }
}
