package game.pandemic.messaging.messengers.persistent;

import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;

public interface IGeneralPurposePersistentConnectionMessenger<T> extends IUnicastAndMulticastPersistentConnectionMessenger<T>, IBroadcastPersistentConnectionMessenger<T>, IGeneralPurposeMessenger<T> {
}
