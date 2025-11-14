package game.pandemic.game.action.effect;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.board.Field;
import game.pandemic.game.events.GameEvent;
import game.pandemic.game.events.MoveGameEvent;
import game.pandemic.game.player.Player;
import game.pandemic.jackson.JacksonView;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveActionEffect extends ActionEffect {
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Player movedPlayer;
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Field toField;

    public MoveActionEffect(final Player executingPlayer, final Player movedPlayer, final Field toField) {
        super(executingPlayer);
        this.movedPlayer = movedPlayer;
        this.toField = toField;
    }

    @Override
    public List<GameEvent> createEvents() {
        return List.of(new MoveGameEvent(this.movedPlayer, this.movedPlayer.getCurrentField(), this.toField));
    }
}
