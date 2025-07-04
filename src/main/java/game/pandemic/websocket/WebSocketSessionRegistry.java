package game.pandemic.websocket;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class WebSocketSessionRegistry<A extends IWebSocketAuthenticationObject> {
    private final Map<Long, List<WebSocketSession>> authenticationObjectToSessions = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, Long> sessionToAuthenticationObject = new ConcurrentHashMap<>();

    private final IWebSocketAuthenticationObjectRepository<A> webSocketAuthenticationObjectRepository;

    public void addSession(final WebSocketSession session, final A authenticationObject) {
        this.authenticationObjectToSessions.computeIfAbsent(authenticationObject.getId(), a -> Collections.synchronizedList(new LinkedList<>())).add(session);
        this.sessionToAuthenticationObject.put(session, authenticationObject.getId());
    }

    public void removeSession(final WebSocketSession session) {
        final Long id = this.sessionToAuthenticationObject.remove(session);
        if (id != null) {
            final List<WebSocketSession> sessions = this.authenticationObjectToSessions.get(id);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    this.authenticationObjectToSessions.remove(id);
                }
            }
        }
    }

    public List<WebSocketSession> findSessionsForAuthenticationObject(final A authenticationObject) {
        return this.authenticationObjectToSessions.get(authenticationObject.getId());
    }

    public Optional<A> findAuthenticationObjectForSession(final WebSocketSession session) {
        return this.webSocketAuthenticationObjectRepository.findById(this.sessionToAuthenticationObject.get(session));
    }

    public Set<WebSocketSession> findAllSessions() {
        return this.sessionToAuthenticationObject.keySet();
    }
}
