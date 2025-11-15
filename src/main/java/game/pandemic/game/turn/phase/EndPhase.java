package game.pandemic.game.turn.phase;

import game.pandemic.game.events.CreateTurnGameEvent;
import game.pandemic.game.turn.Turn;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EndPhase extends Phase<EndPhase> {
    public EndPhase(final Turn turn) {
        super(turn);
        next();
    }

    @Override
    public void next() {
        this.turn.processEvent(new CreateTurnGameEvent());
    }

    @Override
    protected boolean isOver() {
        return true;
    }

    @Override
    protected Phase<?> createNextPhase() {
        return null;
    }
}
