package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.game.action.effect.ActionEffect;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExecuteActionEffectGameEvent extends GameEvent {
    @OneToOne
    private ActionEffect actionEffect;

    @Override
    public void apply(final Game game) {
        final GameEvent event = this.actionEffect.createEvent();
        game.processEvent(event);
        game.getCurrentTurn().getCurrentPhase().next();
    }
}
