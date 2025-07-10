package game.pandemic.user;

import game.pandemic.auth.Account;
import game.pandemic.chat.chats.global.GlobalChatService;
import game.pandemic.websocket.auth.AccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AccessTokenService<User> accessTokenService;
    private final GlobalChatService globalChatService;
    private final UserRepository userRepository;

    public String getAccessTokenForRegisteredUser(final Account account) {
        final User user = getOrCreateUserForAccount(account);
        return this.accessTokenService.createAccessTokenForObject(user);
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
        final User saved = this.userRepository.save(user);
        this.globalChatService.addMember(saved);
        return saved;
    }

    public String getAccessTokenForGuestUser(final String username) {
        final User user = createUserWithUsername(username);
        return this.accessTokenService.createAccessTokenForObject(user);
    }

    private User createUserWithUsername(final String username) {
        final User user = new User(username);
        return generateAccessTokenAndSaveUser(user);
    }

    public Optional<User> findUserByAccessToken(final String accessToken) {
        return this.accessTokenService.parseAccessTokenToObject(accessToken);
    }
}
