package game.pandemic.websocket;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class WebSocketSessionRegistry<A extends IWebSocketAuthenticationObject> {
    public static final String AUTHENTICATION_OBJECT_SESSION_ATTRIBUTE_NAME = "auth";

    private final Map<Long, Set<WebSocketSession>> authenticationObjectToSessions = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, Long> sessionToAuthenticationObject = new ConcurrentHashMap<>();

    public void addSession(final WebSocketSession session, final A authenticationObject) {
        this.authenticationObjectToSessions.computeIfAbsent(authenticationObject.getId(), a -> Collections.synchronizedSet(new HashSet<>())).add(session);
        addAuthenticationObjectToSession(session, authenticationObject);
        this.sessionToAuthenticationObject.put(session, authenticationObject.getId());
    }

    public void removeSession(final WebSocketSession session) {
        final Long id = this.sessionToAuthenticationObject.remove(session);
        if (id != null) {
            final Set<WebSocketSession> sessions = this.authenticationObjectToSessions.get(id);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    this.authenticationObjectToSessions.remove(id);
                }
            }
        }
    }

    public Set<WebSocketSession> findSessionsForAuthenticationObject(final A authenticationObject) {
        return new HashSet<>(this.authenticationObjectToSessions.getOrDefault(authenticationObject.getId(), new HashSet<>()));
    }

    public Optional<A> findAuthenticationObjectForSession(final WebSocketSession session) {
        return convertSessionToAuthenticationObject(session);
    }

    public Set<WebSocketSession> findAllSessions() {
        return new HashSet<>(this.sessionToAuthenticationObject.keySet());
    }

    public Set<A> findAllAuthenticationObjects() {
        return this.sessionToAuthenticationObject.keySet().stream()
                .map(this::convertSessionToAuthenticationObject)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    protected void addAuthenticationObjectToSession(final WebSocketSession session, final A authenticationObject) {
        session.getAttributes().put(AUTHENTICATION_OBJECT_SESSION_ATTRIBUTE_NAME, authenticationObject);
    }

    @SuppressWarnings("unchecked")
    protected Optional<A> convertSessionToAuthenticationObject(final WebSocketSession session) {
        final Object authenticationObjectValue = session.getAttributes().get(AUTHENTICATION_OBJECT_SESSION_ATTRIBUTE_NAME);
        if (authenticationObjectValue instanceof IWebSocketAuthenticationObject) {
            return Optional.of((A) authenticationObjectValue);
        }
        return Optional.empty();
    }
}
