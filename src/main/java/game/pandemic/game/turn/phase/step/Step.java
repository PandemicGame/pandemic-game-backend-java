package game.pandemic.game.turn.phase.step;

import game.pandemic.game.turn.phase.Phase;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Step<P extends Phase<P>> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    protected P phase;

    protected Step(final P phase) {
        this.phase = phase;
    }
}
