package game.pandemic.game.card.player;

import game.pandemic.util.Color;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
public class EpidemicCard extends PlayerCard {
    public static final String TITLE = "Epidemic";
    public static final Color COLOR = new Color(0, 100, 0);

    public EpidemicCard() {
        super(TITLE, COLOR);
    }
}
