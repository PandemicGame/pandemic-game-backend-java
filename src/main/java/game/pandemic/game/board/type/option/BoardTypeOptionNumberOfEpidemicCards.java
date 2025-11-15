package game.pandemic.game.board.type.option;

import game.pandemic.game.GameDifficulty;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardTypeOptionNumberOfEpidemicCards extends BoardTypeOption {
    private int numberOfEpidemicCardsEasy;
    private int numberOfEpidemicCardsMedium;
    private int numberOfEpidemicCardsHard;

    public int getNumberOfEpidemicCards(final GameDifficulty difficulty) throws IllegalArgumentException {
        if (difficulty == null) {
            throw new IllegalArgumentException("Parameter \"difficulty\" may not be null.");
        }
        return switch (difficulty) {
            case EASY -> this.numberOfEpidemicCardsEasy;
            case MEDIUM -> this.numberOfEpidemicCardsMedium;
            case HARD -> this.numberOfEpidemicCardsHard;
        };
    }
}
