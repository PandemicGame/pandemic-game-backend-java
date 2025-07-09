package game.pandemic.messaging.delegators;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IUnicastMessenger;

import java.util.Map;
import java.util.function.BiPredicate;

public interface IUnicastMessengerDelegator<T> extends IUnicastMessenger<T>, IMessengerDelegator<T> {
    Map<Class<? extends T>, IUnicastMessenger<? extends T>> getUnicastMessengersForTypes();

    @SuppressWarnings("unchecked")
    default boolean delegateUnicast(final T target, final BiPredicate<T, IUnicastMessenger<T>> unicastFunction) {
        final Map<Class<? extends T>, IUnicastMessenger<? extends T>> unicastMessengerMap = getUnicastMessengersForTypes();
        if (target != null) {
            final IUnicastMessenger<? extends T> messenger = unicastMessengerMap.get(target.getClass());
            if (messenger != null) {
                unicastFunction.test(target, (IUnicastMessenger<T>) messenger);
            }
        }
        return false;
    }

    @Override
    default boolean unicast(final T target, final Object message, final Class<? extends JacksonView.Any> view) {
        return delegateUnicast(target, (t, m) -> m.unicast(t, message, view));
    }

    @Override
    default boolean unicast(final T target, final Object message) {
        return delegateUnicast(target, (t, m) -> m.unicast(t, message));
    }

    @Override
    default boolean unicast(final T target, final String message) {
        return delegateUnicast(target, (t, m) -> m.unicast(t, message));
    }
}
