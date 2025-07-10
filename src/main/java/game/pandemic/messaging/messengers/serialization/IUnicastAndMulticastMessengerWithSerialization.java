package game.pandemic.messaging.messengers.serialization;

public interface IUnicastAndMulticastMessengerWithSerialization<T> extends IUnicastMessengerWithSerialization<T>, IMulticastMessengerWithSerialization<T> {
}
