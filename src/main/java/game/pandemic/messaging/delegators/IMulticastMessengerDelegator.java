package game.pandemic.messaging.delegators;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IMulticastMessenger;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public interface IMulticastMessengerDelegator<T> extends IMulticastMessenger<T> {
    <C extends T> Map<T, Boolean> delegateMulticast(final Set<T> targets, final BiFunction<Set<C>, IMulticastMessenger<C>, Map<C, Boolean>> multicastFunction);

    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final Object message, final Class<? extends JacksonView.Any> view) {
        return delegateMulticast(targets, (t, m) -> m.multicast(t, message, view));
    }

    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final Object message) {
        return delegateMulticast(targets, (t, m) -> m.multicast(t, message));
    }

    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final String message) {
        return delegateMulticast(targets, (t, m) -> m.multicast(t, message));
    }
}
