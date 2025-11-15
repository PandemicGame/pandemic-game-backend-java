package game.pandemic.game.action;

import game.pandemic.game.player.Player;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionFactory {
    private final Reflections reflections;

    public ActionFactory() {
        this.reflections = new Reflections(Action.class.getPackageName());
    }

    public List<Action> createAllActions(final Player player) {
        return this.reflections.getSubTypesOf(Action.class).stream()
                .filter(clazz -> isValidActionClass(clazz, player))
                .map(clazz -> {
                    try {
                        final Constructor<? extends Action> constructor = clazz.getConstructor(Player.class);
                        return constructor.newInstance(player);
                    } catch (final NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ignored) {
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private boolean isValidActionClass(final Class<? extends Action> actionClass, final Player player) {
        return !Modifier.isAbstract(actionClass.getModifiers()) && !Modifier.isInterface(actionClass.getModifiers()) && (
                (IGeneralAction.class.isAssignableFrom(actionClass) && !player.getRole().getAbility().getRemovedGeneralActions().contains(actionClass.getName())) ||
                (IRoleAction.class.isAssignableFrom(actionClass) && player.getRole().getAbility().getAdditionalRoleActions().contains(actionClass.getName()))
        );
    }
}
