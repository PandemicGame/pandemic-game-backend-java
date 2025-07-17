package game.pandemic.lobby.events;

import game.pandemic.game.Game;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.game.role.LobbyMemberRoleAssociation;
import game.pandemic.lobby.Lobby;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociations;

    @Override
    public void apply(final Lobby lobby) {
        new Game(lobby, this.boardType, this.lobbyMemberRoleAssociations);
    }
}
