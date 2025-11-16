package game.pandemic.game.action.effect;

import game.pandemic.game.Game;
import game.pandemic.game.effect.Effect;
import game.pandemic.game.player.Player;
import game.pandemic.game.turn.phase.ActionPhase;
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

    @Override
    public boolean isAvailable(final Game game) {
        return super.isAvailable(game) && game.isCurrentPlayer(this.executingPlayer) &&
                game.getCurrentTurn().getCurrentPhase() instanceof ActionPhase actionPhase &&
                actionPhase.getAvailableActions().stream()
                        .flatMap(action -> action.getAvailableEffects().stream())
                        .anyMatch(effect -> effect.equals(this));
    }
}
