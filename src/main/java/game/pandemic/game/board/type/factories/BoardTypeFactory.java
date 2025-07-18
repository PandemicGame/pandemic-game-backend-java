package game.pandemic.game.board.type.factories;

import game.pandemic.game.board.location.Location;
import game.pandemic.game.board.location.LocationCode;
import game.pandemic.game.board.location.LocationRepository;
import game.pandemic.game.board.type.BoardSlot;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.game.board.type.BoardTypeCreationException;
import game.pandemic.game.plague.Plague;
import game.pandemic.game.plague.PlagueCode;
import game.pandemic.game.plague.PlagueRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BoardTypeFactory {
    public static final double POSITION_ADJUSTMENT = 5.0;

    protected final LocationRepository locationRepository;
    protected final PlagueRepository plagueRepository;

    public BoardType createBoardType() throws BoardTypeCreationException {
        try {
            return new BoardType(getName(), findStartingLocationOrThrow(), createBoardSlots());
        } catch (final NoSuchElementException e) {
            throw new BoardTypeCreationException(e);
        }
    }

    protected abstract String getName();

    protected abstract Location findStartingLocationOrThrow() throws NoSuchElementException;

    protected abstract List<BoardSlot> createBoardSlots() throws NoSuchElementException;

    protected Location findLocationOrThrow(final LocationCode locationCode) throws NoSuchElementException {
        return findLocation(locationCode).orElseThrow();
    }

    protected Optional<Location> findLocation(final LocationCode locationCode) {
        return this.locationRepository.findByCode(locationCode);
    }

    protected List<Location> findLocationsOrThrow(final LocationCode... locationCodes) throws NoSuchElementException {
        return findLocations(locationCodes).orElseThrow();
    }

    protected Optional<List<Location>> findLocations(final LocationCode... locationCodes) {
        final List<Location> locations = this.locationRepository.findAllByCodeIn(locationCodes);
        if (locations.size() == locationCodes.length) {
            return Optional.of(locations);
        } else {
            return Optional.empty();
        }
    }

    protected Plague findPlagueOrThrow(final PlagueCode plagueCode) throws NoSuchElementException {
        return findPlague(plagueCode).orElseThrow();
    }

    protected Optional<Plague> findPlague(final PlagueCode plagueCode) {
        return this.plagueRepository.findByCode(plagueCode);
    }
}
