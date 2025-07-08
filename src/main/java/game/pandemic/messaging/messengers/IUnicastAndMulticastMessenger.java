package game.pandemic.messaging.messengers;

public interface IUnicastAndMulticastMessenger<T> extends IUnicastMessenger<T>, IMulticastMessenger<T> {
}
