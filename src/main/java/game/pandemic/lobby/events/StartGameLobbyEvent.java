package game.pandemic.lobby.events;

import game.pandemic.game.Game;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.game.role.LobbyMemberRoleAssociation;
import game.pandemic.lobby.Lobby;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameLobbyEvent extends LobbyEvent {
    @ManyToOne
    private BoardType boardType;
    @OneToMany(cascade = CascadeType.ALL)
    private List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociations;
    @Getter
    @OneToOne(cascade = CascadeType.PERSIST)
    private Game game;

    public StartGameLobbyEvent(final BoardType boardType, final List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociations) {
        this.boardType = boardType;
        this.lobbyMemberRoleAssociations = lobbyMemberRoleAssociations;
    }

    @Override
    public void apply(final Lobby lobby) {
        this.game = new Game(lobby, this.boardType, this.lobbyMemberRoleAssociations);
    }
}
