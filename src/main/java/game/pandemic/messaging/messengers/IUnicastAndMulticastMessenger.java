package game.pandemic.messaging.messengers;

import java.util.Map;
import java.util.Set;

public interface IUnicastAndMulticastMessenger<T> extends IUnicastMessenger<T>, IMulticastMessenger<T> {
    @Override
    default Map<T, Boolean> multicast(final Set<T> targets, final String message) {
        return multicastEvaluation(targets, t -> unicast(t, message));
    }
}
