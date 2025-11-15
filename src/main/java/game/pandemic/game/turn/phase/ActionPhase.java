package game.pandemic.game.turn.phase;

import game.pandemic.game.action.Action;
import game.pandemic.game.events.StartStepGameEvent;
import game.pandemic.game.turn.Turn;
import game.pandemic.game.turn.phase.step.ActionExecutionStep;
import game.pandemic.game.turn.phase.step.Step;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionPhase extends Phase<ActionPhase> {
    private int numberOfActionExecutionSteps;

    public ActionPhase(final Turn turn, final int numberOfActionExecutionSteps) {
        super(turn);
        turn.processEvent(new StartStepGameEvent<>(this, new ActionExecutionStep(this)));
        this.numberOfActionExecutionSteps = numberOfActionExecutionSteps;
    }

    @Override
    public void next() {
        if (!isOver()) {
            this.turn.processEvent(new StartStepGameEvent<>(this, new ActionExecutionStep(this)));
        } else {
            switchToNextPhase();
        }
    }

    @Override
    protected boolean isOver() {
        return this.steps.size() == this.numberOfActionExecutionSteps;
    }

    @Override
    protected EndPhase createNextPhase() {
        return new EndPhase(this.turn);
    }

    public List<Action> getAvailableActions() {
        final Step<ActionPhase> step = getCurrentStep();
        if (step instanceof ActionExecutionStep actionExecutionStep) {
            return actionExecutionStep.getAvailableActions();
        }
        return new LinkedList<>();
    }
}
