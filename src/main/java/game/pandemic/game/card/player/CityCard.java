package game.pandemic.game.card.player;

import game.pandemic.game.board.Field;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class CityCard extends PlayerCard {
    private Field associatedField;

    public CityCard(final Field associatedField) {
        super(associatedField.getName(), associatedField.getColor());
        this.associatedField = associatedField;
    }
}
