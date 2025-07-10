package game.pandemic.user;

import game.pandemic.auth.Account;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, IWebSocketAuthenticationObjectRepository<User> {
    Optional<User> findUserByAccount(final Account account);
}
