package game.pandemic.lobby.websocket;

import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.websocket.WebSocketSessionRegistry;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import org.springframework.stereotype.Component;

@Component
public class UserLobbyMemberWebSocketSessionRegistry extends WebSocketSessionRegistry<UserLobbyMember> {
    public UserLobbyMemberWebSocketSessionRegistry(final IWebSocketAuthenticationObjectRepository<UserLobbyMember> webSocketAuthenticationObjectRepository) {
        super(webSocketAuthenticationObjectRepository);
    }
}
