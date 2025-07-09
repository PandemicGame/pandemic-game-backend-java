package game.pandemic.websocket;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class WebSocketSessionRegistry<A extends IWebSocketAuthenticationObject> {
    private final Map<Long, Set<WebSocketSession>> authenticationObjectToSessions = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, Long> sessionToAuthenticationObject = new ConcurrentHashMap<>();

    private final IWebSocketAuthenticationObjectRepository<A> webSocketAuthenticationObjectRepository;

    public void addSession(final WebSocketSession session, final A authenticationObject) {
        this.authenticationObjectToSessions.computeIfAbsent(authenticationObject.getId(), a -> Collections.synchronizedSet(new HashSet<>())).add(session);
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
        return this.authenticationObjectToSessions.getOrDefault(authenticationObject.getId(), new HashSet<>());
    }

    public Optional<A> findAuthenticationObjectForSession(final WebSocketSession session) {
        final Long id = this.sessionToAuthenticationObject.get(session);
        if (id != null) {
            return this.webSocketAuthenticationObjectRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }

    public Set<WebSocketSession> findAllSessions() {
        return this.sessionToAuthenticationObject.keySet();
    }

    public Set<A> findAllAuthenticationObjects() {
        return this.authenticationObjectToSessions.keySet().stream()
                .map(this.webSocketAuthenticationObjectRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
