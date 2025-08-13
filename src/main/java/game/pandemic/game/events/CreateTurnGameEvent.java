package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.game.turn.Turn;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CreateTurnGameEvent extends GameEvent {
    @ManyToOne
    private Game game;

    @Override
    public void apply(final Game game) {
        game.addTurn(new Turn(this));
    }
}
