package game.pandemic.user;

import game.pandemic.websocket.auth.AccessTokenService;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class UserAccessTokenService extends AccessTokenService<User> {
    public UserAccessTokenService(final SecretKey key,
                                  final IWebSocketAuthenticationObjectRepository<User> repository) {
        super(key, repository);
    }
}
