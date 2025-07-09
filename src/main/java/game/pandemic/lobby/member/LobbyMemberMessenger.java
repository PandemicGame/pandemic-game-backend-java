package game.pandemic.lobby.member;

import game.pandemic.lobby.websocket.UserLobbyMemberWebSocketMessenger;
import game.pandemic.messaging.delegators.IUnicastAndMulticastMessengerDelegator;
import game.pandemic.messaging.messengers.IMulticastMessenger;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LobbyMemberMessenger implements IUnicastAndMulticastMessengerDelegator<LobbyMember> {
    private final UserLobbyMemberWebSocketMessenger userLobbyMemberWebSocketMessenger;

    @SuppressWarnings("unchecked")
    @Override
    public <C extends LobbyMember> boolean delegateUnicast(
            final LobbyMember target,
            final BiPredicate<C, IUnicastMessenger<C>> unicastFunction
    ) {
        if (target instanceof UserLobbyMember userLobbyMember) {
            return unicastFunction.test((C) userLobbyMember, (IUnicastMessenger<C>) this.userLobbyMemberWebSocketMessenger);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends LobbyMember> Map<LobbyMember, Boolean> delegateMulticast(
            final Set<LobbyMember> targets,
            final BiFunction<Set<C>, IMulticastMessenger<C>, Map<C, Boolean>> multicastFunction
    ) {
        final Set<C> userLobbyMembers = (Set<C>) targets.stream()
                .filter(UserLobbyMember.class::isInstance)
                .map(UserLobbyMember.class::cast)
                .collect(Collectors.toSet());
        final Map<LobbyMember, Boolean> targetsWithSuccessStatus =
                new HashMap<>(multicastFunction.apply(userLobbyMembers, (IMulticastMessenger<C>) this.userLobbyMemberWebSocketMessenger));

        targets.stream()
                .filter(target -> !(target instanceof UserLobbyMember))
                .forEach(target -> targetsWithSuccessStatus.put(target, false));

        return targetsWithSuccessStatus;
    }
}
