package game.pandemic.lobby.events;

import game.pandemic.game.Game;
import game.pandemic.lobby.Lobby;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class StartGameLobbyEvent extends LobbyEvent {
    @Override
    public void apply(final Lobby lobby) {
        new Game(lobby);
    }
}
