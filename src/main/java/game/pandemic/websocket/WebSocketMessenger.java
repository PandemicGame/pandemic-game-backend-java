package game.pandemic.websocket;

import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Log4j2
public abstract class WebSocketMessenger<A extends IWebSocketAuthenticationObject> implements IGeneralPurposeMessenger<A> {
    protected final WebSocketSessionRegistry<A> webSocketSessionRegistry;
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
        return multicastToSessions(sessions, message);
    }

    protected boolean multicastToSessions(final Set<WebSocketSession> sessions, final String message) {
        boolean isMulticastSuccessful = false;
        for (final WebSocketSession session : sessions) {
            if (unicastToSession(session, message)) {
                isMulticastSuccessful = true;
            }
        }
        return isMulticastSuccessful;
    }

    protected boolean unicastToSession(final WebSocketSession session, final String message) {
        try {
            session.sendMessage(new TextMessage(message));
            return true;
        } catch (final IOException e) {
            log.warn("An Exception occurred during sending of message to session: " + session.getId());
            return false;
        }
    }

    @Override
    public Map<A, Boolean> multicast(final Set<A> targets, final Object message, final Class<? extends JacksonView.Any> view) {
        return this.objectMapper.serialize(message, view, s -> multicast(targets, s), () -> multicastFailure(targets));
    }

    @Override
    public Map<A, Boolean> multicast(final Set<A> targets, final Object message) {
        return this.objectMapper.serialize(message, s -> multicast(targets, s), () -> multicastFailure(targets));
    }

    protected Map<A, Boolean> multicastFailure(final Set<A> targets) {
        return multicastEvaluation(targets, t -> false);
    }

    @Override
    public Map<A, Boolean> multicast(final Set<A> targets, final String message) {
        return multicastEvaluation(targets, t -> unicast(t, message));
    }

    protected Map<A, Boolean> multicastEvaluation(final Set<A> targets, final Predicate<A> successConverter) {
        final Map<A, Boolean> targetsWithSuccessStatus = new HashMap<>();
        for (final A target : targets) {
            targetsWithSuccessStatus.put(target, successConverter.test(target));
        }
        return targetsWithSuccessStatus;
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
