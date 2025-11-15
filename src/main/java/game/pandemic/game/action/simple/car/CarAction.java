package game.pandemic.game.action.simple.car;

import game.pandemic.game.action.effect.MoveActionEffect;
import game.pandemic.game.action.simple.MoveAction;
import game.pandemic.game.player.Player;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CarAction extends MoveAction {
    protected CarAction(final Player executingPlayer) {
        super(executingPlayer);
    }

    @Override
    protected List<MoveActionEffect> createAvailableMoveEffects() {
        return this.executingPlayer.getCurrentField().getConnections().stream()
                .map(field -> new MoveActionEffect(this.executingPlayer, this.executingPlayer, field))
                .toList();
    }
}
