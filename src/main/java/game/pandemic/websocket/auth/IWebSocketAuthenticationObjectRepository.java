package game.pandemic.websocket.auth;

import java.util.Optional;
import java.util.UUID;

public interface IWebSocketAuthenticationObjectRepository<A extends IWebSocketAuthenticationObject> {
    Optional<A> findById(final Long id);
    Optional<A> findByAccessToken(final UUID accessToken);
}
