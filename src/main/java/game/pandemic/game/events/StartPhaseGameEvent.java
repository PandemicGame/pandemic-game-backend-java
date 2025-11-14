package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.game.turn.Turn;
import game.pandemic.game.turn.phase.Phase;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StartPhaseGameEvent extends GameEvent {
    @ManyToOne
    private Turn turn;
    @ManyToOne
    private Phase phase;

    @Override
    public void apply(final Game game) {
        this.turn.setCurrentPhase(this.phase);
    }
}
