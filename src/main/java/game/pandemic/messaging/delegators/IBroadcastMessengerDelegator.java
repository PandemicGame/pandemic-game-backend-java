package game.pandemic.messaging.delegators;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IBroadcastMessenger;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IBroadcastMessengerDelegator<T> extends IBroadcastMessenger<T>, IMessengerDelegator<T> {
    Set<IBroadcastMessenger<? extends T>> getBroadcastMessengers();

    @SuppressWarnings("unchecked")
    default Map<T, Boolean> delegateBroadcast(final Function<IBroadcastMessenger<T>, Map<T, Boolean>> broadcastFunction) {
        return getBroadcastMessengers().stream()
                .map(messenger -> broadcastFunction.apply((IBroadcastMessenger<T>) messenger))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

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
