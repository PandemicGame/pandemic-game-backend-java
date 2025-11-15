package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.game.GameDifficulty;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.game.role.LobbyMemberRoleAssociation;
import game.pandemic.lobby.Lobby;
import game.pandemic.lobby.events.StartGameLobbyEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

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
        final BoardType boardType = this.startGameLobbyEvent.getBoardType();
        final List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociationList = this.startGameLobbyEvent.getLobbyMemberRoleAssociations();
        game.initialize(
                this.lobby,
                boardType,
                Game.DEFAULT_NUMBER_OF_ACTIONS_PER_TURN,
                boardType.getStartingNumberOfHandCards().getStartingNumberOfHandCards(lobbyMemberRoleAssociationList.size()),
                boardType.getNumberOfEpidemicCards().getNumberOfEpidemicCards(GameDifficulty.EASY),
                lobbyMemberRoleAssociationList
        );
    }

    public Game createGame() {
        return new Game(this);
    }
}
