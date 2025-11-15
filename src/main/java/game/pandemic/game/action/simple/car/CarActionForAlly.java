package game.pandemic.game.action.simple.car;

import game.pandemic.game.Game;
import game.pandemic.game.action.simple.IMoveActionForAlly;
import game.pandemic.game.player.Player;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarActionForAlly extends CarAction implements IMoveActionForAlly {
    public CarActionForAlly(final Game game, final Player executingPlayer) {
        super(game, executingPlayer);
    }
}
