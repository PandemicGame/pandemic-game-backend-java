package game.pandemic.game.card.player.event;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;

public final class EventCardFactory {
    private final Reflections reflections;

    public EventCardFactory() {
        this.reflections = new Reflections(EventCard.class.getPackageName());
    }

    public List<EventCard> createEventCards() {
        return this.reflections.getSubTypesOf(EventCard.class).stream()
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .map(this::createEventCard)
                .filter(Objects::nonNull)
                .toList();
    }

    private EventCard createEventCard(final Class<? extends EventCard> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (final NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            return null;
        }
    }
}
