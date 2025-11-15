package game.pandemic.game.action.simple;

import game.pandemic.game.Game;
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
    protected MoveAction(final Game game, final Player executingPlayer) {
        super(game, executingPlayer);
    }

    @Override
    protected final List<ActionEffect> createAvailableEffects() {
        return new ArrayList<>(createAvailableMoveEffects());
    }

    protected List<Player> getMovablePlayers() {
        return List.of(this.executingPlayer);
    }

    protected abstract List<MoveActionEffect> createAvailableMoveEffects();
}
