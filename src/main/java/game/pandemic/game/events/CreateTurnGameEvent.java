package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.game.turn.Turn;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class CreateTurnGameEvent extends GameEvent {
    @ManyToOne
    private Turn turn;

    @Override
    public void apply(final Game game) {
        this.turn = new Turn(game);
        game.addTurn(this.turn);
    }
}
