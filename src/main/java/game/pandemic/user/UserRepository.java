package game.pandemic.user;

import game.pandemic.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByAccount(final Account account);
    Optional<User> findUserByAccessToken(final UUID accessToken);
}
