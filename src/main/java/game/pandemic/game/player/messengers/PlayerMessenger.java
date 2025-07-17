package game.pandemic.game.player.messengers;

import game.pandemic.game.player.Player;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerMessenger implements IUnicastMessenger<Player> {
    private final IUnicastMessenger<LobbyMember> lobbyMemberMessenger;

    @Override
    public boolean unicast(final Player target, final Object message, final Class<? extends JacksonView.Any> view) {
        return this.lobbyMemberMessenger.unicast(target.getLobbyMember(), message, view);
    }

    @Override
    public boolean unicast(final Player target, final Object message) {
        return this.lobbyMemberMessenger.unicast(target.getLobbyMember(), message);
    }

    @Override
    public boolean unicast(final Player target, final String message) {
        return this.lobbyMemberMessenger.unicast(target.getLobbyMember(), message);
    }
}
