package game.pandemic.jackson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Log4j2
public class ObjectMapper {
    @FunctionalInterface
    protected interface MappingFunction<T, R> {
        R apply(final T t) throws IOException;
    }

    @RequiredArgsConstructor
    @Getter
    protected static class CollectionSerializationException extends RuntimeException {
        private final IOException cause;
    }

    protected static final BiConsumer<Object, IOException> DEFAULT_SERIALIZATION_EXCEPTION_HANDLER =
            (o, e) -> log.warn("An exception occurred during serialization of object \"" + o + "\": " + e.getMessage());
    protected static final BiConsumer<String, IOException> DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER =
            (s, e) -> log.warn("An exception occurred during deserialization of \"" + s + "\": " + e.getMessage());

    private final com.fasterxml.jackson.databind.ObjectMapper mapper;

    public <T> T serialize(final Object object,
                           final Class<? extends JacksonView.Any> view,
                           final Function<String, T> callback,
                           final Supplier<T> defaultValueSupplier) {
        return map(
                object,
                o -> serialize(o, view),
                callback,
                defaultValueSupplier,
                DEFAULT_SERIALIZATION_EXCEPTION_HANDLER
        );
    }

    public <T> T serialize(final Object object,
                           final Function<String, T> callback,
                           final Supplier<T> defaultValueSupplier) {
        return map(
                object,
                this::serialize,
                callback,
                defaultValueSupplier,
                DEFAULT_SERIALIZATION_EXCEPTION_HANDLER
        );
    }

    protected String serialize(final Object object, final Class<?> view) throws IOException {
        return serialize(object, o -> this.mapper.writerWithView(view).writeValueAsString(o));
    }

    protected String serialize(final Object object) throws IOException {
        return serialize(object, Object.class);
    }

    protected String serialize(final Object object,
                               final MappingFunction<Object, String> serializer) throws IOException {
        if (object instanceof Collection<?> collection) {
            return serializeCollection(collection, serializer);
        } else {
            return serializer.apply(object);
        }
    }

    protected String serializeCollection(final Collection<?> collection,
                                         final MappingFunction<Object, String> serializer) throws IOException {
        try {
            final List<String> serializedElements = collection.stream()
                    .map(element -> serializeCollectionElement(element, serializer))
                    .toList();
            return "[" + String.join(",", serializedElements) + "]";
        } catch (final CollectionSerializationException e) {
            throw e.getCause();
        }
    }

    protected String serializeCollectionElement(final Object element,
                                                final MappingFunction<Object, String> serializer) throws CollectionSerializationException {
        try {
            return serializer.apply(element);
        } catch (final IOException e) {
            throw new CollectionSerializationException(e);
        }
    }

    public <T> void deserialize(final String string,
                                final Class<T> targetClass,
                                final Class<? extends JacksonView.Any> view,
                                final Consumer<T> callback) {
        map(
                string,
                s -> deserialize(s, targetClass, view),
                callback,
                DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER
        );
    }

    public <T> void deserialize(final String string,
                                final Class<T> targetClass,
                                final Consumer<T> callback) {
        map(
                string,
                s -> deserialize(s, targetClass),
                callback,
                DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER
        );
    }

    protected <T> T deserialize(final String string, final Class<T> targetClass, final Class<?> view) throws IOException {
        return this.mapper.readerWithView(view).readValue(string, targetClass);
    }

    protected <T> T deserialize(final String string, final Class<T> targetClass) throws IOException {
        return deserialize(string, targetClass, Object.class);
    }

    protected <T, R> void map(final T input,
                              final MappingFunction<T, R> mappingFunction,
                              final Consumer<R> callback,
                              final BiConsumer<T, IOException> exceptionConsumer) {
        map(input, mappingFunction, exceptionConsumer).ifPresent(callback);
    }

    protected <T, M, R> R map(final T input,
                              final MappingFunction<T, M> mappingFunction,
                              final Function<M, R> callback,
                              final Supplier<R> defaultValueSupplier,
                              final BiConsumer<T, IOException> exceptionConsumer) {
        final Optional<M> optional = map(input, mappingFunction, exceptionConsumer);
        if (optional.isPresent()) {
            return callback.apply(optional.get());
        } else {
            return defaultValueSupplier.get();
        }
    }

    protected <T, R> Optional<R> map(final T input,
                                     final MappingFunction<T, R> mappingFunction,
                                     final BiConsumer<T, IOException> exceptionConsumer) {
        try {
            final R output = mappingFunction.apply(input);
            return Optional.of(output);
        } catch (final IOException e) {
            exceptionConsumer.accept(input, e);
            return Optional.empty();
        }
    }
}
