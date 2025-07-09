package game.pandemic.messaging.delegators;

import game.pandemic.messaging.messengers.IBroadcastMessenger;
import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;
import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface IGeneralPurposeMessengerDelegator<T> extends IGeneralPurposeMessenger<T>, IUnicastAndMulticastMessengerDelegator<T>, IBroadcastMessengerDelegator<T> {
    Map<Class<? extends T>, IGeneralPurposeMessenger<? extends T>> getGeneralPurposeMessengersForType();

    @Override
    default Map<Class<? extends T>, IUnicastAndMulticastMessenger<? extends T>> getUnicastAndMulticastMessengersForTypes() {
        return new HashMap<>(getGeneralPurposeMessengersForType());
    }

    @Override
    default Set<IBroadcastMessenger<? extends T>> getBroadcastMessengers() {
        return new HashSet<>(getGeneralPurposeMessengersForType().values());
    }
}
