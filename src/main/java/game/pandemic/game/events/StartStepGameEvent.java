package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.game.turn.phase.Phase;
import game.pandemic.game.turn.phase.step.Step;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StartStepGameEvent<P extends Phase<P>> extends GameEvent {
    @ManyToOne(targetEntity = Phase.class)
    private P phase;
    @OneToOne(targetEntity = Step.class)
    private Step<P> step;

    @Override
    public void apply(final Game game) {
        this.phase.addStep(this.step);
    }
}
