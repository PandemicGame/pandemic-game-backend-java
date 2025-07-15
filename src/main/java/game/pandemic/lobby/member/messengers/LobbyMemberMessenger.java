package game.pandemic.lobby.member.messengers;

import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.messaging.delegators.IUnicastAndMulticastMessengerDelegator;
import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LobbyMemberMessenger implements IUnicastAndMulticastMessengerDelegator<LobbyMember>, ILobbyMemberMessenger<LobbyMember> {
    private final ILobbyMemberMessenger<UserLobbyMember> userLobbyMemberMessenger;

    @Override
    public Map<Class<? extends LobbyMember>, IUnicastAndMulticastMessenger<? extends LobbyMember>> getUnicastAndMulticastMessengersForTypes() {
        return new HashMap<>(getLobbyMemberMessengersForType());
    }

    protected Map<Class<? extends LobbyMember>, ILobbyMemberMessenger<? extends LobbyMember>> getLobbyMemberMessengersForType() {
        return Map.of(
                UserLobbyMember.class, this.userLobbyMemberMessenger
        );
    }

    @Override
    public boolean closeConnection(final LobbyMember member) {
        return delegateCloseConnection(member);
    }

    @SuppressWarnings("unchecked")
    protected <M extends LobbyMember> boolean delegateCloseConnection(final M member) {
        if (member != null) {
            final ILobbyMemberMessenger<M> messenger = (ILobbyMemberMessenger<M>) getLobbyMemberMessengersForType().get(member.getClass());
            if (messenger != null) {
                return messenger.closeConnection(member);
            }
        }
        return false;
    }
}
