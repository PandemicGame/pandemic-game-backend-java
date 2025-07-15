package game.pandemic.lobby.events;

import game.pandemic.game.Game;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.lobby.Lobby;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StartGameLobbyEvent extends LobbyEvent {
    @ManyToOne
    private BoardType boardType;

    @Override
    public void apply(final Lobby lobby) {
        new Game(lobby, this.boardType);
    }
}
