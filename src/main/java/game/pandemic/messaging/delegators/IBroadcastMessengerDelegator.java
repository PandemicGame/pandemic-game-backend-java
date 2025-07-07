package game.pandemic.messaging.delegators;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IBroadcastMessenger;

import java.util.Map;
import java.util.function.Function;

public interface IBroadcastMessengerDelegator<T> extends IBroadcastMessenger<T> {
    <C extends T> Map<T, Boolean> delegateBroadcast(final Function<IBroadcastMessenger<C>, Map<C, Boolean>> broadcastFunction);

    @Override
    default Map<T, Boolean> broadcast(final Object message, final Class<? extends JacksonView.Any> view) {
        return delegateBroadcast(m -> m.broadcast(message, view));
    }

    @Override
    default Map<T, Boolean> broadcast(final Object message) {
        return delegateBroadcast(m -> m.broadcast(message));
    }

    @Override
    default Map<T, Boolean> broadcast(final String message) {
        return delegateBroadcast(m -> m.broadcast(message));
    }
}
