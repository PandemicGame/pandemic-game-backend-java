package game.pandemic.game;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.board.Board;
import game.pandemic.game.board.Field;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.game.plague.Plague;
import game.pandemic.game.player.Player;
import game.pandemic.game.role.LobbyMemberRoleAssociation;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.Lobby;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Game implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @OneToOne
    @JsonView(JacksonView.Read.class)
    private Lobby lobby;
    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn(name = "player_index")
    @JsonView(JacksonView.Read.class)
    private List<Player> playersInTurnOrder;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    private Board board;

    public Game(final Lobby lobby, final BoardType boardType, final List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociations) {
        this.lobby = lobby;
        this.lobby.setGame(this);
        this.board = new Board(boardType);
        this.playersInTurnOrder = createPlayersInTurnOrderList(lobbyMemberRoleAssociations);
    }

    private List<Player> createPlayersInTurnOrderList(final List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociations) {
        final Field startingField = this.board.getStartingField();
        return lobbyMemberRoleAssociations.stream()
                .map(lobbyMemberRoleAssociation -> new Player(
                        lobbyMemberRoleAssociation.getLobbyMember(),
                        startingField,
                        lobbyMemberRoleAssociation.getRole()
                ))
                .toList();
    }

    public Optional<Player> findPlayerByLobbyMember(final LobbyMember lobbyMember) {
        return this.playersInTurnOrder.stream()
                .filter(player -> player.containsLobbyMember(lobbyMember))
                .findFirst();
    }

    @JsonView(JacksonView.Read.class)
    public Set<Plague> getPlagues() {
        return this.board.getPlagues();
    }
}
