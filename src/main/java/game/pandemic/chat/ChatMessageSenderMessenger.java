package game.pandemic.chat;

import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.websocket.UserLobbyMemberWebSocketMessenger;
import game.pandemic.messaging.delegators.IGeneralPurposeMessengerDelegator;
import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;
import game.pandemic.user.User;
import game.pandemic.user.websocket.UserWebSocketMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChatMessageSenderMessenger implements IGeneralPurposeMessengerDelegator<ChatMessageSender> {
    private final UserWebSocketMessenger userWebSocketMessenger;
    private final UserLobbyMemberWebSocketMessenger userLobbyMemberWebSocketMessenger;

    @Override
    public Map<Class<? extends ChatMessageSender>, IGeneralPurposeMessenger<? extends ChatMessageSender>> getGeneralPurposeMessengersForType() {
        return Map.of(
                User.class, this.userWebSocketMessenger,
                UserLobbyMember.class, this.userLobbyMemberWebSocketMessenger
        );
    }
}
