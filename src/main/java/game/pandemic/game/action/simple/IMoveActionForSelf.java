package game.pandemic.game.action.simple;

import game.pandemic.game.action.IGeneralAction;
import game.pandemic.game.player.Player;

import java.util.List;

public interface IMoveActionForSelf extends IMoveAction, IGeneralAction {
    default List<Player> getMovablePlayers() {
        return List.of(getExecutingPlayer());
    }
}
