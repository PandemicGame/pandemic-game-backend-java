package game.pandemic.game.card;

import game.pandemic.game.board.Field;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class InfectionCard extends Card {
    @ManyToOne
    private Field associatedField;

    public InfectionCard(final Field associatedField) {
        super(associatedField.getName(), associatedField.getColor());
        this.associatedField = associatedField;
    }
}
