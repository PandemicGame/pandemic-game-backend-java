package game.pandemic.game.turn.phase;

import game.pandemic.game.events.StartPhaseGameEvent;
import game.pandemic.game.player.Player;
import game.pandemic.game.turn.Turn;
import game.pandemic.game.turn.phase.step.Step;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Phase<P extends Phase<P>> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    protected Turn turn;
    @OneToMany(cascade = CascadeType.ALL)
    protected List<Step<P>> steps;

    protected Phase(final Turn turn) {
        this.turn = turn;
        this.steps = new LinkedList<>();
    }

    public Step<P> getCurrentStep() {
        return this.steps.get(this.steps.size() - 1);
    }

    public void addStep(final Step<P> step) {
        this.steps.add(step);
    }

    public Player getPlayer() {
        return this.turn.getPlayer();
    }

    public void next() {
        switchToNextPhaseIfOver();
    }

    protected void switchToNextPhaseIfOver() {
        if (this.isOver()) {
            switchToNextPhase();
        }
    }

    protected abstract boolean isOver();

    protected void switchToNextPhase() {
        this.turn.processEvent(new StartPhaseGameEvent(this.turn, createNextPhase()));
    }

    protected abstract Phase<?> createNextPhase();
}
