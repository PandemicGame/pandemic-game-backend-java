package game.pandemic.messaging.messengers;

public interface IGeneralPurposeMessenger<T> extends IUnicastAndMulticastMessenger<T>, IBroadcastMessenger<T> {
}
