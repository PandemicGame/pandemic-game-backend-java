package game.pandemic.game;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.event.IEventContext;
import game.pandemic.game.board.Board;
import game.pandemic.game.board.Field;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.game.events.CreateGameEvent;
import game.pandemic.game.events.CreateTurnGameEvent;
import game.pandemic.game.events.GameEvent;
import game.pandemic.game.plague.Plague;
import game.pandemic.game.player.Player;
import game.pandemic.game.role.LobbyMemberRoleAssociation;
import game.pandemic.game.turn.Turn;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.Lobby;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Game implements IWebSocketData, IEventContext<Game, GameEvent> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @JsonView(JacksonView.Read.class)
    private Long lobbyId;
    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn(name = "player_index")
    @JsonView(JacksonView.Read.class)
    private List<Player> playersInTurnOrder;
    private int currentPlayerIndex;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    private Board board;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    @OrderColumn(name = "turn_index")
    @JsonView(JacksonView.Read.class)
    private List<Turn> turns;
    @OneToOne(cascade = CascadeType.ALL)
    private CreateGameEvent creationEvent;

    public Game(final CreateGameEvent creationEvent) {
        this.creationEvent = creationEvent;
        this.creationEvent.apply(this);
    }

    public void initialize(final Lobby lobby, final BoardType boardType, final List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociations) {
        this.lobbyId = lobby.getId();
        this.board = new Board(boardType);
        this.playersInTurnOrder = createPlayersInTurnOrderList(lobbyMemberRoleAssociations);
        this.currentPlayerIndex = 0;
        this.turns = new LinkedList<>();
        processEvent(new CreateTurnGameEvent(this));
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

    public Player getCurrentPlayer() {
        return this.playersInTurnOrder.get(this.currentPlayerIndex);
    }

    public boolean isCurrentPlayer(final Player player) {
        return getCurrentPlayer().equals(player);
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

    public void addTurn(final Turn turn) {
        this.turns.add(turn);
    }

    @Override
    public void processEvent(final GameEvent event) {
        this.creationEvent.appendEvent(event);
        event.apply(this);
    }

    @Override
    public void reset() {
        this.creationEvent.apply(this);
    }

    @Override
    public void restore() {
        this.creationEvent.applyAll(this);
    }
}
