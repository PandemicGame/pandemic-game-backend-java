package game.pandemic.game.action.simple.car;

import game.pandemic.game.Game;
import game.pandemic.game.action.simple.IMoveActionForSelf;
import game.pandemic.game.player.Player;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarActionForSelf extends CarAction implements IMoveActionForSelf {
    public CarActionForSelf(final Game game, final Player executingPlayer) {
        super(game, executingPlayer);
    }
}
