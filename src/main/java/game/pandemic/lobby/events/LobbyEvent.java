package game.pandemic.lobby.events;

import game.pandemic.event.Event;
import game.pandemic.lobby.Lobby;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LobbyEvent extends Event<Lobby, LobbyEvent> {
}
