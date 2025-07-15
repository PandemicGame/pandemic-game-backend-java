package game.pandemic.lobby.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.member.messengers.ILobbyMemberMessenger;
import game.pandemic.messaging.messengers.persistent.IMulticastPersistentConnectionMessenger;
import game.pandemic.websocket.WebSocketSessionRegistry;
import game.pandemic.websocket.auth.WebSocketAuthenticationObjectMessenger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Component
public class UserLobbyMemberWebSocketMessenger extends WebSocketAuthenticationObjectMessenger<UserLobbyMember> implements ILobbyMemberMessenger<UserLobbyMember> {
    public UserLobbyMemberWebSocketMessenger(final WebSocketSessionRegistry<UserLobbyMember> webSocketSessionRegistry,
                                             final IMulticastPersistentConnectionMessenger<WebSocketSession> webSocketMessenger,
                                             final ObjectMapper objectMapper) {
        super(webSocketSessionRegistry, webSocketMessenger, objectMapper);
    }

    @Override
    public boolean closeConnection(final UserLobbyMember member) {
        return this.webSocketMessenger.closeConnection(this.webSocketSessionRegistry.findSessionsForAuthenticationObject(member)).entrySet().stream()
                .allMatch(Map.Entry::getValue);
    }
}
