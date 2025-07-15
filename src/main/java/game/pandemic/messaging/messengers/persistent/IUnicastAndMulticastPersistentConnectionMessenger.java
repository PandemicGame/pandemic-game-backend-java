package game.pandemic.messaging.messengers.persistent;

import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;

public interface IUnicastAndMulticastPersistentConnectionMessenger<T> extends IUnicastPersistentConnectionMessenger<T>, IMulticastPersistentConnectionMessenger<T>, IUnicastAndMulticastMessenger<T> {
}
