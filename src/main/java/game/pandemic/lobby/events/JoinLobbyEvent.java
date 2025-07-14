package game.pandemic.lobby.events;

import game.pandemic.lobby.Lobby;
import game.pandemic.lobby.member.LobbyMember;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinLobbyEvent extends LobbyMemberEvent {
    public JoinLobbyEvent(final LobbyMember lobbyMember) {
        super(lobbyMember);
    }

    @Override
    public void apply(final Lobby lobby) {
        lobby.addMember(this.lobbyMember);
    }
}
