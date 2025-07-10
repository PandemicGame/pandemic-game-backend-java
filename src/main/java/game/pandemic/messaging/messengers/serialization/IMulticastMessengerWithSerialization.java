package game.pandemic.messaging.messengers.serialization;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IMulticastMessenger;

import java.util.Map;
import java.util.Set;

public interface IMulticastMessengerWithSerialization<T> extends IMulticastMessenger<T>, IMessengerWithSerialization<T> {
    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final Object message, final Class<? extends JacksonView.Any> view) {
        return serialize(message, view, s -> multicast(targets, s), () -> multicastFailure(targets));
    }

    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final Object message) {
        return serialize(message, s -> multicast(targets, s), () -> multicastFailure(targets));
    }
}
