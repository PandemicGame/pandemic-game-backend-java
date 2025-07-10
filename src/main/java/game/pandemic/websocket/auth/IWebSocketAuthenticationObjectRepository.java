package game.pandemic.websocket.auth;

import java.util.Optional;

public interface IWebSocketAuthenticationObjectRepository<A extends IWebSocketAuthenticationObject> {
    Optional<A> findById(final Long id);
}
