package game.pandemic.user;

import game.pandemic.auth.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String getAccessTokenForRegisteredUser(final Account account) {
        final User user = getOrCreateUserForAccount(account);
        return user.getAccessTokenString();
    }

    private User getOrCreateUserForAccount(final Account account) {
        final Optional<User> userOptional = this.userRepository.findUserByAccount(account);
        return userOptional.orElseGet(() -> createUserForAccount(account));
    }

    private User createUserForAccount(final Account account) {
        final User user = new User(account);
        return generateAccessTokenAndSaveUser(user);
    }

    private User generateAccessTokenAndSaveUser(final User user) {
        user.setAccessToken(UUID.randomUUID());
        return this.userRepository.save(user);
    }

    public String getAccessTokenForGuestUser(final String username) {
        final User user = createUserWithUsername(username);
        return user.getAccessTokenString();
    }

    private User createUserWithUsername(final String username) {
        final User user = new User(username);
        return generateAccessTokenAndSaveUser(user);
    }

    public Optional<User> findUserByAccessToken(final UUID accessToken) {
        return this.userRepository.findUserByAccessToken(accessToken);
    }
}
