package game.pandemic.messaging.messengers.persistent;

import game.pandemic.messaging.messengers.IBroadcastMessenger;

import java.util.Map;

public interface IBroadcastPersistentConnectionMessenger<T> extends IPersistentConnectionMessenger<T>, IBroadcastMessenger<T> {
    Map<T, Boolean> closeConnection();
    Map<T, Boolean> closeConnection(final String message);
}
