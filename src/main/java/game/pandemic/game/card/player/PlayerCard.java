package game.pandemic.game.card.player;

import game.pandemic.game.card.Card;
import game.pandemic.util.Color;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public abstract class PlayerCard extends Card {
    protected PlayerCard(final String title, final Color color) {
        super(title, color);
    }
}
