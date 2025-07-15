package game.pandemic.game.board.type;

import game.pandemic.game.board.location.Location;
import game.pandemic.game.board.location.LocationCode;
import game.pandemic.game.board.location.LocationRepository;
import game.pandemic.game.plague.Plague;
import game.pandemic.game.plague.PlagueCode;
import game.pandemic.game.plague.PlagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static game.pandemic.game.board.location.LocationCode.*;

@Component
@RequiredArgsConstructor
public class WorldMapBoardTypeFactory {
    public static final String WORLD_MAP_NAME = "World Map";

    private final LocationRepository locationRepository;
    private final PlagueRepository plagueRepository;

    public BoardType createWorldMap() throws BoardTypeCreationException {
        try {
            return new BoardType(WORLD_MAP_NAME, findLocationOrThrow(ATLANTA), createBoardSlots());
        } catch (final NoSuchElementException e) {
            throw new BoardTypeCreationException(e);
        }
    }

    private List<BoardSlot> createBoardSlots() throws NoSuchElementException {
        return Stream.of(
                createBlue(),
                createYellow(),
                createBlack(),
                createRed()
        ).flatMap(Collection::stream).toList();
    }

    private List<BoardSlot> createBlue() throws NoSuchElementException {
        final Plague blue = findPlagueOrThrow(PlagueCode.BLUE);
        return List.of(
                new BoardSlot(0, 0, blue, findLocationOrThrow(SAN_FRANCISCO), findLocationsOrThrow(CHICAGO, LOS_ANGELES, MANILA, TOKYO)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(CHICAGO), findLocationsOrThrow(SAN_FRANCISCO, LOS_ANGELES, MEXICO_CITY, ATLANTA, MONTREAL)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(MONTREAL), findLocationsOrThrow(CHICAGO, WASHINGTON, NEW_YORK_CITY)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(NEW_YORK_CITY), findLocationsOrThrow(MONTREAL, WASHINGTON, LONDON, MADRID)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(ATLANTA), findLocationsOrThrow(CHICAGO, WASHINGTON, MIAMI)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(WASHINGTON), findLocationsOrThrow(ATLANTA, MIAMI, NEW_YORK_CITY, MONTREAL)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(MADRID), findLocationsOrThrow(NEW_YORK_CITY, SAO_PAULO, ALGIERS, PARIS, LONDON)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(LONDON), findLocationsOrThrow(NEW_YORK_CITY, MADRID, PARIS, ESSEN)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(PARIS), findLocationsOrThrow(LONDON, MADRID, ALGIERS, MILAN, ESSEN)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(ESSEN), findLocationsOrThrow(LONDON, PARIS, MILAN, SAINT_PETERSBURG)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(MILAN), findLocationsOrThrow(ESSEN, PARIS, ISTANBUL)),
                new BoardSlot(0, 0, blue, findLocationOrThrow(SAINT_PETERSBURG), findLocationsOrThrow(ESSEN, ISTANBUL, MOSCOW))
        );
    }

    private List<BoardSlot> createYellow() throws NoSuchElementException {
        final Plague yellow = findPlagueOrThrow(PlagueCode.YELLOW);
        return List.of(
                new BoardSlot(0, 0, yellow, findLocationOrThrow(LOS_ANGELES), findLocationsOrThrow(SAN_FRANCISCO, SYDNEY, MEXICO_CITY, CHICAGO)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(MEXICO_CITY), findLocationsOrThrow(LOS_ANGELES, LIMA, BOGOTA, MIAMI, CHICAGO)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(MIAMI), findLocationsOrThrow(MEXICO_CITY, BOGOTA, WASHINGTON, ATLANTA)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(BOGOTA), findLocationsOrThrow(MEXICO_CITY, LIMA, BUENOS_AIRES, SAO_PAULO, MIAMI)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(LIMA), findLocationsOrThrow(MEXICO_CITY, SANTIAGO, BOGOTA)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(SANTIAGO), findLocationsOrThrow(LIMA)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(BUENOS_AIRES), findLocationsOrThrow(BOGOTA, SAO_PAULO)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(SAO_PAULO), findLocationsOrThrow(BOGOTA, BUENOS_AIRES, LAGOS, MADRID)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(LAGOS), findLocationsOrThrow(SAO_PAULO, KINSHASA, KHARTOUM)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(KINSHASA), findLocationsOrThrow(LAGOS, JOHANNESBURG, KHARTOUM)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(JOHANNESBURG), findLocationsOrThrow(KINSHASA, KHARTOUM)),
                new BoardSlot(0, 0, yellow, findLocationOrThrow(KHARTOUM), findLocationsOrThrow(LAGOS, KINSHASA, JOHANNESBURG, CAIRO))
        );
    }

    private List<BoardSlot> createBlack() throws NoSuchElementException {
        final Plague black = findPlagueOrThrow(PlagueCode.BLACK);
        return List.of(
                new BoardSlot(0, 0, black, findLocationOrThrow(ALGIERS), findLocationsOrThrow(MADRID, PARIS, ISTANBUL, CAIRO)),
                new BoardSlot(0, 0, black, findLocationOrThrow(CAIRO), findLocationsOrThrow(ALGIERS, ISTANBUL, BAGHDAD, RIYADH, KHARTOUM)),
                new BoardSlot(0, 0, black, findLocationOrThrow(ISTANBUL), findLocationsOrThrow(ALGIERS, CAIRO, BAGHDAD, MOSCOW, SAINT_PETERSBURG, MILAN)),
                new BoardSlot(0, 0, black, findLocationOrThrow(MOSCOW), findLocationsOrThrow(SAINT_PETERSBURG, ISTANBUL, TEHRAN)),
                new BoardSlot(0, 0, black, findLocationOrThrow(TEHRAN), findLocationsOrThrow(MOSCOW, BAGHDAD, KARACHI, NEW_DELHI)),
                new BoardSlot(0, 0, black, findLocationOrThrow(BAGHDAD), findLocationsOrThrow(ISTANBUL, CAIRO, RIYADH, KARACHI, TEHRAN)),
                new BoardSlot(0, 0, black, findLocationOrThrow(RIYADH), findLocationsOrThrow(CAIRO, BAGHDAD, KARACHI)),
                new BoardSlot(0, 0, black, findLocationOrThrow(KARACHI), findLocationsOrThrow(RIYADH, BAGHDAD, TEHRAN, NEW_DELHI, MUMBAI)),
                new BoardSlot(0, 0, black, findLocationOrThrow(NEW_DELHI), findLocationsOrThrow(TEHRAN, KARACHI, MUMBAI, CHENNAI, KOLKATA)),
                new BoardSlot(0, 0, black, findLocationOrThrow(CHENNAI), findLocationsOrThrow(MUMBAI, NEW_DELHI, KOLKATA, BANGKOK, JAKARTA)),
                new BoardSlot(0, 0, black, findLocationOrThrow(KOLKATA), findLocationsOrThrow(NEW_DELHI, CHENNAI, BANGKOK, HONG_KONG))
        );
    }

    private List<BoardSlot> createRed() throws NoSuchElementException {
        final Plague red = findPlagueOrThrow(PlagueCode.RED);
        return List.of(
                new BoardSlot(0, 0, red, findLocationOrThrow(JAKARTA), findLocationsOrThrow(CHENNAI, BANGKOK, HO_CHI_MINH_CITY, SYDNEY)),
                new BoardSlot(0, 0, red, findLocationOrThrow(SYDNEY), findLocationsOrThrow(JAKARTA, MANILA, LOS_ANGELES)),
                new BoardSlot(0, 0, red, findLocationOrThrow(HO_CHI_MINH_CITY), findLocationsOrThrow(JAKARTA, BANGKOK, HONG_KONG, MANILA)),
                new BoardSlot(0, 0, red, findLocationOrThrow(BANGKOK), findLocationsOrThrow(KOLKATA, CHENNAI, JAKARTA, HO_CHI_MINH_CITY, HONG_KONG)),
                new BoardSlot(0, 0, red, findLocationOrThrow(HONG_KONG), findLocationsOrThrow(KOLKATA, BANGKOK, HO_CHI_MINH_CITY, MANILA, TAIPEI, SHANGHAI)),
                new BoardSlot(0, 0, red, findLocationOrThrow(MANILA), findLocationsOrThrow(SYDNEY, SAN_FRANCISCO, TAIPEI, HONG_KONG, HO_CHI_MINH_CITY)),
                new BoardSlot(0, 0, red, findLocationOrThrow(TAIPEI), findLocationsOrThrow(OSAKA, SHANGHAI, HONG_KONG, MANILA)),
                new BoardSlot(0, 0, red, findLocationOrThrow(OSAKA), findLocationsOrThrow(TAIPEI, TOKYO)),
                new BoardSlot(0, 0, red, findLocationOrThrow(TOKYO), findLocationsOrThrow(OSAKA, SAN_FRANCISCO, SEOUL, SHANGHAI)),
                new BoardSlot(0, 0, red, findLocationOrThrow(SEOUL), findLocationsOrThrow(TOKYO, BEIJING, SHANGHAI)),
                new BoardSlot(0, 0, red, findLocationOrThrow(BEIJING), findLocationsOrThrow(SEOUL, SHANGHAI)),
                new BoardSlot(0, 0, red, findLocationOrThrow(SHANGHAI), findLocationsOrThrow(BEIJING, SEOUL, TOKYO, TAIPEI, HONG_KONG))
        );
    }

    private Location findLocationOrThrow(final LocationCode locationCode) throws NoSuchElementException {
        return findLocation(locationCode).orElseThrow();
    }

    private Optional<Location> findLocation(final LocationCode locationCode) {
        return this.locationRepository.findByCode(locationCode);
    }

    private List<Location> findLocationsOrThrow(final LocationCode... locationCodes) throws NoSuchElementException {
        return findLocations(locationCodes).orElseThrow();
    }

    private Optional<List<Location>> findLocations(final LocationCode... locationCodes) {
        final List<Location> locations = this.locationRepository.findAllByCodeIn(locationCodes);
        if (locations.size() == locationCodes.length) {
            return Optional.of(locations);
        } else {
            return Optional.empty();
        }
    }

    private Plague findPlagueOrThrow(final PlagueCode plagueCode) throws NoSuchElementException {
        return findPlague(plagueCode).orElseThrow();
    }

    private Optional<Plague> findPlague(final PlagueCode plagueCode) {
        return this.plagueRepository.findByCode(plagueCode);
    }
}
