package game.pandemic.game.action.simple;

import game.pandemic.game.action.IAction;
import game.pandemic.game.player.Player;

import java.util.List;

public interface IMoveAction extends IAction {
    List<Player> getMovablePlayers();
}
