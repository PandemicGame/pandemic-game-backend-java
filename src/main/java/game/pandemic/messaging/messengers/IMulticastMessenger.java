package game.pandemic.messaging.messengers;

import game.pandemic.jackson.JacksonView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public interface IMulticastMessenger<T> extends IMessenger<T> {
    Map<T, Boolean> multicast(final Set<T> targets, final Object message, final Class<? extends JacksonView.Any> view);
    Map<T, Boolean> multicast(final Set<T> targets, final Object message);
    Map<T, Boolean> multicast(final Set<T> targets, final String message);

    default Map<T, Boolean> multicastFailure(final Set<T> targets) {
        return multicastEvaluation(targets, t -> false);
    }

    default Map<T, Boolean> multicastEvaluation(final Set<T> targets, final Predicate<T> successEvaluator) {
        final Map<T, Boolean> targetsWithSuccessStatus = new HashMap<>();
        for (final T target : targets) {
            targetsWithSuccessStatus.put(target, successEvaluator.test(target));
        }
        return targetsWithSuccessStatus;
    }
}
