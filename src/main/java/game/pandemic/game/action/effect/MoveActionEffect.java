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
    private Field fromField;
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Field toField;

    public MoveActionEffect(final Player executingPlayer, final Player movedPlayer, final Field fromField, final Field toField) {
        super(executingPlayer, movedPlayer);
        this.fromField = fromField;
        this.toField = toField;
    }

    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    public Player getMovedPlayer() {
        return this.targetPlayer;
    }

    @Override
    public List<GameEvent> createEvents() {
        return List.of(new MoveGameEvent(getMovedPlayer(), this.fromField, this.toField));
    }

    @Override
    public boolean requiresApproval() {
        return !this.executingPlayer.equals(this.targetPlayer);
    }
}
