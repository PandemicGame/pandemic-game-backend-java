package game.pandemic.game.action.simple;

import game.pandemic.game.action.IRoleAction;
import game.pandemic.game.player.Player;

import java.util.List;

public interface IMoveActionForAlly extends IMoveAction, IRoleAction {
    default List<Player> getMovablePlayers() {
        final Player executingPlayer = getExecutingPlayer();
        return getGame().getPlayersInTurnOrder().stream()
                .filter(player -> !player.equals(executingPlayer))
                .toList();
    }
}
