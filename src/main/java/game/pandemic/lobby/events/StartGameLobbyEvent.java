package game.pandemic.lobby.events;

import game.pandemic.game.Game;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.game.role.Role;
import game.pandemic.lobby.Lobby;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StartGameLobbyEvent extends LobbyEvent {
    @ManyToOne
    private BoardType boardType;
    @ManyToMany
    private List<Role> roles;

    @Override
    public void apply(final Lobby lobby) {
        new Game(lobby, this.boardType, this.roles);
    }
}
