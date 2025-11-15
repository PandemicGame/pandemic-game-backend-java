package game.pandemic.game.action.simple.car;

import game.pandemic.game.action.IGeneralAction;
import game.pandemic.game.player.Player;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarActionForSelf extends CarAction implements IGeneralAction {
    public CarActionForSelf(final Player executingPlayer) {
        super(executingPlayer);
    }
}
