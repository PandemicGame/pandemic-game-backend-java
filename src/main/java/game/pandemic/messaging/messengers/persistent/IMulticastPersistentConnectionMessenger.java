package game.pandemic.messaging.messengers.persistent;

import game.pandemic.messaging.messengers.IMulticastMessenger;

import java.util.Map;
import java.util.Set;

public interface IMulticastPersistentConnectionMessenger<T> extends IPersistentConnectionMessenger<T>, IMulticastMessenger<T> {
    Map<T, Boolean> closeConnection(final Set<T> targets);
    Map<T, Boolean> closeConnection(final Set<T> targets, final String message);
}
