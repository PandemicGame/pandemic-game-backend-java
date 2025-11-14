package game.pandemic.game.turn;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.event.IEventContext;
import game.pandemic.game.Game;
import game.pandemic.game.action.Action;
import game.pandemic.game.events.GameEvent;
import game.pandemic.game.events.StartPhaseGameEvent;
import game.pandemic.game.player.Player;
import game.pandemic.game.turn.phase.ActionPhase;
import game.pandemic.game.turn.phase.Phase;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Turn implements IWebSocketData, IEventContext<Game, GameEvent> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Game game;
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Player player;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Phase currentPhase;

    public Turn(final Game game) {
        this.game = game;
        this.player = this.game.getCurrentPlayer();
        processEvent(new StartPhaseGameEvent(this, new ActionPhase(this, game.getNumberOfActionsPerTurn())));
    }

    @Override
    public void processEvent(final GameEvent event) {
        this.game.processEvent(event);
    }

    @Override
    public void reset() {
        this.game.reset();
    }

    @Override
    public void restore() {
        this.game.restore();
    }

    @JsonView(JacksonView.Read.class)
    public List<Action> getAvailableActions() {
        if (this.currentPhase instanceof ActionPhase actionPhase) {
            return actionPhase.getAvailableActions();
        }
        return new LinkedList<>();
    }
}
