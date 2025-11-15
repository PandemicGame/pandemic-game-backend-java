package game.pandemic.game.action;

import game.pandemic.game.Game;
import game.pandemic.game.player.Player;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;

public class ActionFactory {
    private final Reflections reflections;

    public ActionFactory() {
        this.reflections = new Reflections(Action.class.getPackageName());
    }

    public List<Action> createAllActions(final Game game, final Player executingPlayer) {
        return this.reflections.getSubTypesOf(Action.class).stream()
                .filter(clazz -> isValidActionClass(clazz, executingPlayer))
                .map(clazz -> createAction(clazz, game, executingPlayer))
                .filter(Objects::nonNull)
                .toList();
    }

    private boolean isValidActionClass(final Class<? extends Action> actionClass, final Player player) {
        return !Modifier.isAbstract(actionClass.getModifiers()) && !Modifier.isInterface(actionClass.getModifiers()) && (
                (IGeneralAction.class.isAssignableFrom(actionClass) && !player.getRole().getAbility().getRemovedGeneralActions().contains(actionClass.getName())) ||
                (IRoleAction.class.isAssignableFrom(actionClass) && player.getRole().getAbility().getAdditionalRoleActions().contains(actionClass.getName()))
        );
    }

    private <A extends Action> Action createAction(final Class<A> clazz, final Game game, final Player executingPlayer) {
        try {
            final Constructor<A> constructor = clazz.getConstructor(Game.class, Player.class);
            return constructor.newInstance(game, executingPlayer);
        } catch (final NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ignored) {
            return null;
        }
    }
}
