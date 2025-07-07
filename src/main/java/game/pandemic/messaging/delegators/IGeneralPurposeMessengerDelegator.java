package game.pandemic.messaging.delegators;

import game.pandemic.messaging.messengers.IGeneralPurposeMessenger;

public interface IGeneralPurposeMessengerDelegator<T> extends IGeneralPurposeMessenger<T>, IUnicastMessengerDelegator<T>, IMulticastMessengerDelegator<T>, IBroadcastMessengerDelegator<T> {
}
