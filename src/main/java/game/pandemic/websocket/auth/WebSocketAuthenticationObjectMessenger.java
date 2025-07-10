package game.pandemic.websocket.auth;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.IMulticastMessenger;
import game.pandemic.messaging.messengers.serialization.IGeneralPurposeMessengerWithSerialization;
import game.pandemic.messaging.messengers.serialization.MessengerWithSerialization;
import game.pandemic.websocket.WebSocketSessionRegistry;
import org.springframework.web.socket.WebSocketSession;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class WebSocketAuthenticationObjectMessenger<A extends IWebSocketAuthenticationObject> extends MessengerWithSerialization<A> implements IGeneralPurposeMessengerWithSerialization<A> {
    protected final WebSocketSessionRegistry<A> webSocketSessionRegistry;
    protected final IMulticastMessenger<WebSocketSession> webSocketMessenger;

    protected WebSocketAuthenticationObjectMessenger(final WebSocketSessionRegistry<A> webSocketSessionRegistry,
                                                     final IMulticastMessenger<WebSocketSession> webSocketMessenger,
                                                     final ObjectMapper objectMapper) {
        super(objectMapper);
        this.webSocketSessionRegistry = webSocketSessionRegistry;
        this.webSocketMessenger = webSocketMessenger;
    }

    @Override
    public boolean unicast(final A target, final String message) {
        final Set<WebSocketSession> sessions = this.webSocketSessionRegistry.findSessionsForAuthenticationObject(target);
        return this.webSocketMessenger.multicast(sessions, message).entrySet().stream()
                .anyMatch(Map.Entry::getValue);
    }

    @Override
    public Map<A, Boolean> broadcast(final String message) {
        return multicast(this.webSocketSessionRegistry.findAllAuthenticationObjects(), message);
    }

    @Override
    public Map<A, Boolean> broadcastFailure() {
        return this.webSocketSessionRegistry.findAllAuthenticationObjects().stream()
                .map(authenticationObject -> new AbstractMap.SimpleEntry<>(authenticationObject, false))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
