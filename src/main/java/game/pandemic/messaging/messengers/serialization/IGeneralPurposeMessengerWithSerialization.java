package game.pandemic.messaging.messengers.serialization;

import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;

public interface IGeneralPurposeMessengerWithSerialization<T> extends IGeneralPurposeMessenger<T>, IUnicastAndMulticastMessengerWithSerialization<T>, IBroadcastMessengerWithSerialization<T> {
}
