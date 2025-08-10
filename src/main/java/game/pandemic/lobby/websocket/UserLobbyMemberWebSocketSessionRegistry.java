package game.pandemic.lobby.websocket;

import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.websocket.WebSocketSessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserLobbyMemberWebSocketSessionRegistry extends WebSocketSessionRegistry<UserLobbyMember> {
}
