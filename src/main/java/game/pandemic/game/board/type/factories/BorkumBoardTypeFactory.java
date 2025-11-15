package game.pandemic.game.board.type.factories;

import game.pandemic.game.board.location.Location;
import game.pandemic.game.board.location.LocationRepository;
import game.pandemic.game.board.type.BoardSettings;
import game.pandemic.game.board.type.BoardSlot;
import game.pandemic.game.board.type.option.BoardTypeOptionNumberOfEpidemicCards;
import game.pandemic.game.board.type.option.BoardTypeOptionStartingNumberOfHandCards;
import game.pandemic.game.plague.Plague;
import game.pandemic.game.plague.PlagueCode;
import game.pandemic.game.plague.PlagueRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

import static game.pandemic.game.board.location.LocationCode.*;

@Component
public class BorkumBoardTypeFactory extends BoardTypeFactory {
    public BorkumBoardTypeFactory(final LocationRepository locationRepository, final PlagueRepository plagueRepository) {
        super(locationRepository, plagueRepository);
    }

    @Override
    protected String getName() {
        return "Borkum";
    }

    @Override
    protected Location findStartingLocationOrThrow() throws NoSuchElementException {
        return findLocationOrThrow(BORKUM_REEDE);
    }

    @Override
    protected List<BoardSlot> createBoardSlots() throws NoSuchElementException {
        final Plague blue = findPlagueOrThrow(PlagueCode.BLUE);
        return List.of(
                new BoardSlot(6.662, 53.587, blue, findLocationOrThrow(BORKUM_CORE), findLocationsOrThrow(BORKUM_REEDE, BORKUM_OSTLAND)),
                new BoardSlot(6.754, 53.564, blue, findLocationOrThrow(BORKUM_REEDE), findLocationsOrThrow(BORKUM_CORE, BORKUM_OSTLAND)),
                new BoardSlot(6.726, 53.604, blue, findLocationOrThrow(BORKUM_OSTLAND), findLocationsOrThrow(BORKUM_CORE, BORKUM_REEDE))
        );
    }

    @Override
    protected BoardSettings createBoardSettings() {
        return new BoardSettings(
                500,
                0.005,
                14,
                13,
                13,
                6.7,
                53.6,
                6.5,
                6.9,
                53.5,
                53.7
        );
    }

    @Override
    protected BoardTypeOptionStartingNumberOfHandCards createBoardTypeOptionStartingNumberOfHandCards() {
        return new BoardTypeOptionStartingNumberOfHandCards(new BoardTypeOptionStartingNumberOfHandCards.StartingNumberOfHandCardsByNumberOfPlayersList(
                1
        ));
    }

    @Override
    protected BoardTypeOptionNumberOfEpidemicCards createBoardTypeOptionNumberOfEpidemicCards() {
        return new BoardTypeOptionNumberOfEpidemicCards(
                0,
                0,
                0
        );
    }
}
