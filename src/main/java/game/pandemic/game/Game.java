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
    public static final int DEFAULT_NUMBER_OF_ACTIONS_PER_TURN = 4;

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
    private int numberOfActionsPerTurn;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    @OrderColumn(name = "turn_index")
    @JsonView(JacksonView.Read.class)
    private List<Turn> turns;
    private int startingNumberOfHandCards;
    private int numberOfEpidemicCards;
    @OneToMany(cascade = CascadeType.ALL)
    private List<GameEvent> eventChain;

    public Game(final CreateGameEvent creationEvent) {
        this.eventChain = new LinkedList<>();
        this.eventChain.add(creationEvent);
        creationEvent.apply(this);
    }

    public void initialize(final Lobby lobby,
                           final BoardType boardType,
                           final int numberOfActionsPerTurn,
                           final int startingNumberOfHandCards,
                           final int numberOfEpidemicCards,
                           final List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociations) {
        this.lobbyId = lobby.getId();
        this.board = new Board(boardType);
        this.numberOfActionsPerTurn = numberOfActionsPerTurn;
        this.playersInTurnOrder = createPlayersInTurnOrderList(lobbyMemberRoleAssociations);
        this.currentPlayerIndex = -1;
        this.turns = new LinkedList<>();
        this.startingNumberOfHandCards = startingNumberOfHandCards;
        this.numberOfEpidemicCards = numberOfEpidemicCards;
        processEvent(new CreateTurnGameEvent());
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

    public void nextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.playersInTurnOrder.size();
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

    public Turn getCurrentTurn() {
        return this.turns.get(this.turns.size() - 1);
    }

    public void addTurn(final Turn turn) {
        this.turns.add(turn);
    }

    @Override
    public void processEvent(final GameEvent event) {
        this.eventChain.add(event);
        event.apply(this);
    }

    @Override
    public void reset() {
        this.eventChain.get(0).apply(this);
    }

    @Override
    public void restore() {
        this.eventChain.forEach(event -> event.apply(this));
    }
}
