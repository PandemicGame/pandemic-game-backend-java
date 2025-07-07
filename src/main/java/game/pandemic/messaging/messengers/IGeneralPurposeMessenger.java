package game.pandemic.messaging.messengers;

public interface IGeneralPurposeMessenger<T> extends IUnicastMessenger<T>, IMulticastMessenger<T>, IBroadcastMessenger<T> {
}
