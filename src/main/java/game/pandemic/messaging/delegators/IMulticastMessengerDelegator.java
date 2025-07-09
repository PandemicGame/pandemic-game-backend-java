package game.pandemic.messaging.delegators;

import game.pandemic.jackson.JacksonView;
import game.pandemic.messaging.messengers.IMulticastMessenger;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public interface IMulticastMessengerDelegator<T> extends IMulticastMessenger<T>, IMessengerDelegator<T> {
    Map<Class<? extends T>, IMulticastMessenger<? extends T>> getMulticastMessengersForTypes();

    default Map<T, Boolean> delegateMulticast(final Set<T> targets, final BiFunction<Set<T>, IMulticastMessenger<T>, Map<T, Boolean>> multicastFunction) {
        final Map<Class<? extends T>, IMulticastMessenger<? extends T>> multicastMessengerMap = getMulticastMessengersForTypes();
        final Map<Class<T>, Set<T>> targetsSplitByType = splitTargetsByType(targets);
        return targetsSplitByType.entrySet().stream()
                .map(targetsWithClass -> delegateMulticast(targetsWithClass, multicastMessengerMap, multicastFunction))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @SuppressWarnings("unchecked")
    default Map<Class<T>, Set<T>> splitTargetsByType(final Set<T> targets) {
        return targets.stream()
                .collect(Collectors.groupingBy(
                        target -> (Class<T>) target.getClass(),
                        Collectors.toSet()
                ));
    }

    @SuppressWarnings("unchecked")
    default Map<T, Boolean> delegateMulticast(final Map.Entry<Class<T>, Set<T>> targetsWithClass,
                                              final Map<Class<? extends T>, IMulticastMessenger<? extends T>> multicastMessengerMap,
                                              final BiFunction<Set<T>, IMulticastMessenger<T>, Map<T, Boolean>> multicastFunction) {
        final Set<T> targetsOfClass = targetsWithClass.getValue();
        final IMulticastMessenger<T> messenger = (IMulticastMessenger<T>) multicastMessengerMap.get(targetsWithClass.getKey());
        if (messenger != null) {
            return multicastFunction.apply(targetsOfClass, messenger);
        }
        return multicastFailure(targetsOfClass);
    }

    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final Object message, final Class<? extends JacksonView.Any> view) {
        return delegateMulticast(targets, (t, m) -> m.multicast(t, message, view));
    }

    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final Object message) {
        return delegateMulticast(targets, (t, m) -> m.multicast(t, message));
    }

    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final String message) {
        return delegateMulticast(targets, (t, m) -> m.multicast(t, message));
    }
}
