package game.pandemic.game.turn.phase;

import game.pandemic.game.player.Player;
import game.pandemic.game.turn.Turn;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    protected Turn turn;

    protected Phase(final Turn turn) {
        this.turn = turn;
    }

    public Player getPlayer() {
        return this.turn.getPlayer();
    }

    public void next() {
        switchToNextPhaseIfOver();
    }

    protected void switchToNextPhaseIfOver() {
        if (this.isOver()) {
            switchToNextPhase();
        }
    }

    protected abstract boolean isOver();

    protected void switchToNextPhase() {
    }

    protected abstract Phase createNextPhase();
}
