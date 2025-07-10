package game.pandemic.messaging.messengers.serialization;

import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MessengerWithSerialization<T> implements IMessengerWithSerialization<T> {
    protected final ObjectMapper objectMapper;

    @Override
    public <B> B serialize(final Object message, final Class<? extends JacksonView.Any> view, final Function<String, B> callback, final Supplier<B> defaultValueSupplier) {
        return this.objectMapper.serialize(message, view, callback, defaultValueSupplier);
    }

    @Override
    public <B> B serialize(final Object message, final Function<String, B> callback, final Supplier<B> defaultValueSupplier) {
        return this.objectMapper.serialize(message, callback, defaultValueSupplier);
    }
}
