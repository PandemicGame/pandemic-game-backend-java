package game.pandemic.lobby.member;

import game.pandemic.lobby.websocket.UserLobbyMemberWebSocketMessenger;
import game.pandemic.messaging.delegators.IUnicastAndMulticastMessengerDelegator;
import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LobbyMemberMessenger implements IUnicastAndMulticastMessengerDelegator<LobbyMember> {
    private final UserLobbyMemberWebSocketMessenger userLobbyMemberWebSocketMessenger;

    @Override
    public Map<Class<? extends LobbyMember>, IUnicastAndMulticastMessenger<? extends LobbyMember>> getUnicastAndMulticastMessengersForTypes() {
        return Map.of(
                UserLobbyMember.class, this.userLobbyMemberWebSocketMessenger
        );
    }
}
