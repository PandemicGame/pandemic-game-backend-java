package game.pandemic.chat;

import game.pandemic.messaging.delegators.IGeneralPurposeMessengerDelegator;
import game.pandemic.messaging.messengers.IBroadcastMessenger;
import game.pandemic.messaging.messengers.IMulticastMessenger;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import game.pandemic.user.User;
import game.pandemic.user.websocket.UserWebSocketMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMessageSenderMessenger implements IGeneralPurposeMessengerDelegator<ChatMessageSender> {
    private final UserWebSocketMessenger userWebSocketMessenger;

    @SuppressWarnings("unchecked")
    @Override
    public <C extends ChatMessageSender> boolean delegateUnicast(
            final ChatMessageSender target,
            final BiPredicate<C, IUnicastMessenger<C>> unicastFunction
    ) {
        if (target instanceof User user) {
            return unicastFunction.test((C) user, (IUnicastMessenger<C>) this.userWebSocketMessenger);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends ChatMessageSender> Map<ChatMessageSender, Boolean> delegateMulticast(
            final Set<ChatMessageSender> targets,
            final BiFunction<Set<C>, IMulticastMessenger<C>, Map<C, Boolean>> multicastFunction
    ) {
        final Set<C> users = (Set<C>) targets.stream()
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .collect(Collectors.toSet());
        final Map<ChatMessageSender, Boolean> targetsWithSuccessStatus =
                new HashMap<>(multicastFunction.apply(users, (IMulticastMessenger<C>) this.userWebSocketMessenger));

        targets.stream()
                .filter(target -> !(target instanceof User))
                .forEach(target -> targetsWithSuccessStatus.put(target, false));

        return targetsWithSuccessStatus;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends ChatMessageSender> Map<ChatMessageSender, Boolean> delegateBroadcast(
            final Function<IBroadcastMessenger<C>, Map<C, Boolean>> broadcastFunction
    ) {
        return new HashMap<>(broadcastFunction.apply((IBroadcastMessenger<C>) this.userWebSocketMessenger));
    }
}
