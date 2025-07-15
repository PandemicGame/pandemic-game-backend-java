package game.pandemic.lobby.events;

import game.pandemic.lobby.Lobby;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CloseLobbyEvent extends LobbyEvent {
    @Override
    public void apply(final Lobby lobby) {
        lobby.setClosed(true);
    }
}
