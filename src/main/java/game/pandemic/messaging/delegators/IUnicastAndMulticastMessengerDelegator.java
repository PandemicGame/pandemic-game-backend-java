package game.pandemic.messaging.delegators;

import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;

public interface IUnicastAndMulticastMessengerDelegator<T> extends IUnicastAndMulticastMessenger<T>, IUnicastMessengerDelegator<T>, IMulticastMessengerDelegator<T> {
}
