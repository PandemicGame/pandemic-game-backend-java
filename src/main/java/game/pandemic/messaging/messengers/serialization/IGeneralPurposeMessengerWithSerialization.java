package game.pandemic.messaging.messengers.serialization;

public interface IGeneralPurposeMessengerWithSerialization<T> extends IUnicastAndMulticastMessengerWithSerialization<T>, IBroadcastMessengerWithSerialization<T> {
}
