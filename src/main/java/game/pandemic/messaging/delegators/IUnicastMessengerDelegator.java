package game.pandemic.messaging.delegators;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IUnicastMessenger;

import java.util.function.BiPredicate;

public interface IUnicastMessengerDelegator<T> extends IUnicastMessenger<T> {
    <C extends T> boolean delegateUnicast(final T target, final BiPredicate<C, IUnicastMessenger<C>> unicastFunction);

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
