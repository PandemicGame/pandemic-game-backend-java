package game.pandemic.websocket.auth;

import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;
import game.pandemic.messaging.messengers.IMulticastMessenger;
import game.pandemic.websocket.WebSocketSessionRegistry;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class WebSocketAuthenticationObjectMessenger<A extends IWebSocketAuthenticationObject> implements IGeneralPurposeMessenger<A> {
    protected final WebSocketSessionRegistry<A> webSocketSessionRegistry;
    protected final IMulticastMessenger<WebSocketSession> webSocketMessenger;
    protected final ObjectMapper objectMapper;

    @Override
    public boolean unicast(final A target, final Object message, final Class<? extends JacksonView.Any> view) {
        return this.objectMapper.serialize(message, view, s -> unicast(target, s), () -> false);
    }

    @Override
    public boolean unicast(final A target, final Object message) {
        return this.objectMapper.serialize(message, s -> unicast(target, s), () -> false);
    }

    @Override
    public boolean unicast(final A target, final String message) {
        final Set<WebSocketSession> sessions = this.webSocketSessionRegistry.findSessionsForAuthenticationObject(target);
        return this.webSocketMessenger.multicast(sessions, message).entrySet().stream()
                .anyMatch(Map.Entry::getValue);
    }

    @Override
    public Map<A, Boolean> multicast(final Set<A> targets, final Object message, final Class<? extends JacksonView.Any> view) {
        return this.objectMapper.serialize(message, view, s -> multicast(targets, s), () -> multicastFailure(targets));
    }

    @Override
    public Map<A, Boolean> multicast(final Set<A> targets, final Object message) {
        return this.objectMapper.serialize(message, s -> multicast(targets, s), () -> multicastFailure(targets));
    }

    @Override
    public Map<A, Boolean> multicast(final Set<A> targets, final String message) {
        return multicastEvaluation(targets, t -> unicast(t, message));
    }

    @Override
    public Map<A, Boolean> broadcast(final Object message, final Class<? extends JacksonView.Any> view) {
        return multicast(this.webSocketSessionRegistry.findAllAuthenticationObjects(), message, view);
    }

    @Override
    public Map<A, Boolean> broadcast(final Object message) {
        return multicast(this.webSocketSessionRegistry.findAllAuthenticationObjects(), message);
    }

    @Override
    public Map<A, Boolean> broadcast(final String message) {
        return multicast(this.webSocketSessionRegistry.findAllAuthenticationObjects(), message);
    }
}
