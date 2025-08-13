package game.pandemic.game.events;

import game.pandemic.game.Game;
import game.pandemic.game.board.Field;
import game.pandemic.game.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MoveGameEvent extends GameEvent {
    @ManyToOne
    private Player movedPlayer;
    @ManyToOne
    private Field fromField;
    @ManyToOne
    private Field toField;

    @Override
    public void apply(final Game context) {
        this.movedPlayer.setCurrentField(this.toField);
    }
}
