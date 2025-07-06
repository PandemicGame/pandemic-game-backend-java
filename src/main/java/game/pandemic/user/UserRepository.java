package game.pandemic.user;

import game.pandemic.auth.Account;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, IWebSocketAuthenticationObjectRepository<User> {
    Optional<User> findUserByAccount(final Account account);
    Optional<User> findUserByAccessToken(final UUID accessToken);
    default Optional<User> findByAccessToken(final UUID accessToken) {
        return this.findUserByAccessToken(accessToken);
    }
}
