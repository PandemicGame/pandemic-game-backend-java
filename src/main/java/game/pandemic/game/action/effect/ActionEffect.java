package game.pandemic.game.action.effect;

import game.pandemic.game.effect.Effect;
import game.pandemic.game.player.Player;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public abstract class ActionEffect extends Effect {
    protected ActionEffect(final Player executingPlayer, final Player targetPlayer) {
        super(executingPlayer, targetPlayer);
    }
}
