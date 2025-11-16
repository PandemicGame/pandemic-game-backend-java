package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.game.effect.Effect;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExecuteEffectGameEvent extends GameEvent {
    @OneToOne
    private Effect effect;

    @Override
    public void apply(final Game game) {
        final List<GameEvent> events = this.effect.createEvents();
        for (final GameEvent event : events) {
            game.processEvent(event);
        }
        game.getCurrentTurn().getCurrentPhase().next();
    }
}
