package game.pandemic.game.card.player.event;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.card.player.PlayerCard;
import game.pandemic.jackson.JacksonView;
import game.pandemic.util.Color;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class EventCard extends PlayerCard {
    public static final Color COLOR = new Color(255, 165, 0);

    @JsonView(JacksonView.Read.class)
    private String description;

    protected EventCard(final String title, final String description) {
        super(title, COLOR);
        this.description = description;
    }
}
