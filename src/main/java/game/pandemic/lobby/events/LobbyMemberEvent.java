package game.pandemic.lobby.events;

import game.pandemic.lobby.member.LobbyMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LobbyMemberEvent extends LobbyEvent {
    @ManyToOne
    protected LobbyMember lobbyMember;
}
