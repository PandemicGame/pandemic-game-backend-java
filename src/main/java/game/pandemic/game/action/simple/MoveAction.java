package game.pandemic.game.action.simple;

import game.pandemic.game.action.Action;
import game.pandemic.game.action.effect.ActionEffect;
import game.pandemic.game.action.effect.MoveActionEffect;
import game.pandemic.game.player.Player;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MoveAction extends Action {
    protected MoveAction(final Player executingPlayer) {
        super(executingPlayer);
    }

    @Override
    protected final List<ActionEffect> createAvailableEffects() {
        return new ArrayList<>(createAvailableMoveEffects());
    }

    protected abstract List<MoveActionEffect> createAvailableMoveEffects();
}
