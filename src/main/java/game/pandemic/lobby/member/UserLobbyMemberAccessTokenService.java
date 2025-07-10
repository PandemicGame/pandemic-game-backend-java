package game.pandemic.lobby.member;

import game.pandemic.websocket.auth.AccessTokenService;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class UserLobbyMemberAccessTokenService extends AccessTokenService<UserLobbyMember> {
    public UserLobbyMemberAccessTokenService(final SecretKey key,
                                             final IWebSocketAuthenticationObjectRepository<UserLobbyMember> repository) {
        super(key, repository);
    }
}
