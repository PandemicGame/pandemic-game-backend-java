package game.pandemic.messaging.messengers;

import game.pandemic.jackson.JacksonView;

public interface IUnicastMessenger<T> {
    boolean unicast(final T target, final Object message, final Class<? extends JacksonView.Any> view);
    boolean unicast(final T target, final Object message);
    boolean unicast(final T target, final String message);
}
