package game.pandemic.messaging.messengers;

import game.pandemic.jackson.JacksonView;

import java.util.Map;
import java.util.Set;

public interface IMulticastMessenger<T> {
    Map<T, Boolean> multicast(final Set<T> targets, final Object message, final Class<? extends JacksonView.Any> view);
    Map<T, Boolean> multicast(final Set<T> targets, final Object message);
    Map<T, Boolean> multicast(final Set<T> targets, final String message);
}
