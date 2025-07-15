package game.pandemic.messaging.messengers.persistent;

import game.pandemic.messaging.messengers.IUnicastMessenger;

public interface IUnicastPersistentConnectionMessenger<T> extends IPersistentConnectionMessenger<T>, IUnicastMessenger<T> {
    boolean closeConnection(final T target);
    boolean closeConnection(final T target, final String message);
}
