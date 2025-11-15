package game.pandemic.game.action;

import game.pandemic.game.Game;
import game.pandemic.game.player.Player;

public interface IAction {
    Game getGame();
    Player getExecutingPlayer();
}
