package game.pandemic.game.board.type.option;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTypeOptionStartingNumberOfHandCards extends BoardTypeOption {
    public static class StartingNumberOfHandCardsByNumberOfPlayersList extends LinkedList<Integer> {
        public StartingNumberOfHandCardsByNumberOfPlayersList(final int startingNumberOfHandCardsTwoPlayers,
                                                              final int... startingNumberOfHandCardsMoreThanTwoPlayers) {
            this.add(startingNumberOfHandCardsTwoPlayers);
            for (final int startingNumberOfHandCards : startingNumberOfHandCardsMoreThanTwoPlayers) {
                this.add(startingNumberOfHandCards);
            }
        }
    }

    private List<Integer> startingNumberOfHandCardsByNumberOfPlayers;

    public BoardTypeOptionStartingNumberOfHandCards(final StartingNumberOfHandCardsByNumberOfPlayersList startingNumberOfHandCardsByNumberOfPlayers) {
        this.startingNumberOfHandCardsByNumberOfPlayers = startingNumberOfHandCardsByNumberOfPlayers;
    }

    public int getStartingNumberOfHandCards(final int numberOfPlayers) throws IllegalArgumentException {
        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("Parameter \"numberOfPlayers\" must be at least 2.");
        }
        if (numberOfPlayers - 2 >= this.startingNumberOfHandCardsByNumberOfPlayers.size()) {
            return this.startingNumberOfHandCardsByNumberOfPlayers.get(this.startingNumberOfHandCardsByNumberOfPlayers.size() - 1);
        }
        return this.startingNumberOfHandCardsByNumberOfPlayers.get(numberOfPlayers - 2);
    }
}
