package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.lobby.Lobby;
import game.pandemic.lobby.events.StartGameLobbyEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateGameEvent extends GameEvent {
    @OneToOne
    private Lobby lobby;
    @OneToOne
    private StartGameLobbyEvent startGameLobbyEvent;

    @Override
    public void apply(final Game game) {
        game.initialize(
                this.lobby,
                this.startGameLobbyEvent.getBoardType(),
                Game.DEFAULT_NUMBER_OF_ACTIONS_PER_TURN,
                this.startGameLobbyEvent.getLobbyMemberRoleAssociations()
        );
    }

    public Game createGame() {
        return new Game(this);
    }
}
