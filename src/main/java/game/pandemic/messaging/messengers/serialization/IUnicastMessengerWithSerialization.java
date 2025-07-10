package game.pandemic.messaging.messengers.serialization;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IUnicastMessenger;

public interface IUnicastMessengerWithSerialization<T> extends IUnicastMessenger<T>, IMessengerWithSerialization<T> {
    @Override
    default boolean unicast(final T target, final Object message, final Class<? extends JacksonView.Any> view) {
        return serialize(message, view, s -> unicast(target, s), () -> false);
    }

    @Override
    default boolean unicast(final T target, final Object message) {
        return serialize(message, s -> unicast(target, s), () -> false);
    }
}
