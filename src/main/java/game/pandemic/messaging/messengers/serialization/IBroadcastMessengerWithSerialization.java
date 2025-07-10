package game.pandemic.messaging.messengers.serialization;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IBroadcastMessenger;

import java.util.Map;

public interface IBroadcastMessengerWithSerialization<T> extends IBroadcastMessenger<T>, IMessengerWithSerialization<T> {
    @Override
    default Map<T, Boolean> broadcast(final Object message, final Class<? extends JacksonView.Any> view) {
        return serialize(message, view, this::broadcast, this::broadcastFailure);
    }

    @Override
    default Map<T, Boolean> broadcast(final Object message) {
        return serialize(message, this::broadcast, this::broadcastFailure);
    }
}
