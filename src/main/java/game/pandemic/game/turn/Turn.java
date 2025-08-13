package game.pandemic.game.turn;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.Game;
import game.pandemic.game.action.Action;
import game.pandemic.game.events.CreateTurnGameEvent;
import game.pandemic.game.player.Player;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Turn implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Game game;
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Player player;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    private List<Action> availableActions;
    @OneToOne(cascade = CascadeType.ALL)
    private CreateTurnGameEvent creationEvent;

    public Turn(final CreateTurnGameEvent creationEvent) {
        this.creationEvent = creationEvent;
        initialize();
    }

    private void initialize() {
        this.game = this.creationEvent.getGame();
        this.player = this.game.getCurrentPlayer();
        this.availableActions = new LinkedList<>();
    }
}
