package game.pandemic.messaging.messengers.serialization;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IMessenger;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IMessengerWithSerialization<T> extends IMessenger<T> {
    <B> B serialize(final Object message, final Class<? extends JacksonView.Any> view, final Function<String, B> callback, final Supplier<B> defaultValueSupplier);
    <B> B serialize(final Object message, final Function<String, B> callback, final Supplier<B> defaultValueSupplier);
}
