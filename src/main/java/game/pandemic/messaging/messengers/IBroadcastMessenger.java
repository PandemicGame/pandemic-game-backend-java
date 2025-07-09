package game.pandemic.messaging.messengers;

import game.pandemic.jackson.JacksonView;

import java.util.Map;

public interface IBroadcastMessenger<T> extends IMessenger<T> {
    Map<T, Boolean> broadcast(final Object message, final Class<? extends JacksonView.Any> view);
    Map<T, Boolean> broadcast(final Object message);
    Map<T, Boolean> broadcast(final String message);
}
