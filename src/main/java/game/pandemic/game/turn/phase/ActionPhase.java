package game.pandemic.game.turn.phase;

import game.pandemic.game.action.Action;
import game.pandemic.game.turn.Turn;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionPhase extends Phase {
    private int numberOfActionExecutionSteps;

    public ActionPhase(final Turn turn, final int numberOfActionExecutionSteps) {
        super(turn);
        this.numberOfActionExecutionSteps = numberOfActionExecutionSteps;
    }

    @Override
    protected boolean isOver() {
        return false;
    }

    @Override
    protected Phase createNextPhase() {
        return new ActionPhase(this.turn, this.numberOfActionExecutionSteps);
    }

    public List<Action> getAvailableActions() {
        return new LinkedList<>();
    }
}
