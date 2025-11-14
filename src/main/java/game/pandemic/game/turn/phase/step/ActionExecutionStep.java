package game.pandemic.game.turn.phase.step;

import game.pandemic.game.action.Action;
import game.pandemic.game.turn.phase.ActionPhase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ActionExecutionStep extends Step<ActionPhase> {
    @OneToMany(cascade = CascadeType.ALL)
    private List<Action> availableActions;

    public ActionExecutionStep(final ActionPhase phase) {
        super(phase);
        this.availableActions = new LinkedList<>();
    }
}
