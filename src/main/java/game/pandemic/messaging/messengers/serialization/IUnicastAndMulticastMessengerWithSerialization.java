package game.pandemic.messaging.messengers.serialization;

import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;

public interface IUnicastAndMulticastMessengerWithSerialization<T> extends IUnicastAndMulticastMessenger<T>, IUnicastMessengerWithSerialization<T>, IMulticastMessengerWithSerialization<T> {
}
