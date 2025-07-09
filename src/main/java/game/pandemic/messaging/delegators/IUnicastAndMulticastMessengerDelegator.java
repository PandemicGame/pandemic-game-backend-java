package game.pandemic.messaging.delegators;

import game.pandemic.messaging.messengers.IMulticastMessenger;
import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;
import game.pandemic.messaging.messengers.IUnicastMessenger;

import java.util.HashMap;
import java.util.Map;

public interface IUnicastAndMulticastMessengerDelegator<T> extends IUnicastAndMulticastMessenger<T>, IUnicastMessengerDelegator<T>, IMulticastMessengerDelegator<T> {
    Map<Class<? extends T>, IUnicastAndMulticastMessenger<? extends T>> getUnicastAndMulticastMessengersForTypes();

    @Override
    default Map<Class<? extends T>, IUnicastMessenger<? extends T>> getUnicastMessengersForTypes() {
        return new HashMap<>(getUnicastAndMulticastMessengersForTypes());
    }

    @Override
    default Map<Class<? extends T>, IMulticastMessenger<? extends T>> getMulticastMessengersForTypes() {
        return new HashMap<>(getUnicastAndMulticastMessengersForTypes());
    }
}
