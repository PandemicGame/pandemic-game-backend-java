package game.pandemic.lobby.member.messengers;

import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.messaging.messengers.persistent.IUnicastAndMulticastPersistentConnectionMessenger;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface ILobbyMemberMessenger<M extends LobbyMember> extends IUnicastAndMulticastPersistentConnectionMessenger<M> {
    default boolean closeConnection(final M member, final String message) {
        return closeConnection(member);
    }

    default Map<M, Boolean> closeConnection(final Set<M> members) {
        return closeConnection(members, this::closeConnection);
    }

    default Map<M, Boolean> closeConnection(final Set<M> members, final String message) {
        return closeConnection(members, m -> closeConnection(m, message));
    }

    private Map<M, Boolean> closeConnection(final Set<M> members, final Predicate<M> closeFunction) {
        return members.stream()
                .map(member -> new AbstractMap.SimpleEntry<>(member, closeFunction.test(member)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
