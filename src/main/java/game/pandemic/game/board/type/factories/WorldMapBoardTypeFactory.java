package game.pandemic.game.board.type.factories;

import game.pandemic.game.board.location.Location;
import game.pandemic.game.board.location.LocationRepository;
import game.pandemic.game.board.type.BoardSettings;
import game.pandemic.game.board.type.BoardSlot;
import game.pandemic.game.plague.Plague;
import game.pandemic.game.plague.PlagueCode;
import game.pandemic.game.plague.PlagueRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static game.pandemic.game.board.location.LocationCode.*;

@Component
public class WorldMapBoardTypeFactory extends BoardTypeFactory {
    public WorldMapBoardTypeFactory(final LocationRepository locationRepository, final PlagueRepository plagueRepository) {
        super(locationRepository, plagueRepository);
    }

    @Override
    protected String getName() {
        return "World Map";
    }

    @Override
    protected Location findStartingLocationOrThrow() throws NoSuchElementException {
        return findLocationOrThrow(ATLANTA);
    }

    @Override
    protected List<BoardSlot> createBoardSlots() throws NoSuchElementException {
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
                new BoardSlot(-122.25, 37.47 + POSITION_ADJUSTMENT, blue, findLocationOrThrow(SAN_FRANCISCO), findLocationsOrThrow(CHICAGO, LOS_ANGELES, MANILA, TOKYO)),
                new BoardSlot(-87.38, 41.53, blue, findLocationOrThrow(CHICAGO), findLocationsOrThrow(SAN_FRANCISCO, LOS_ANGELES, MEXICO_CITY, ATLANTA, MONTREAL)),
                new BoardSlot(-73.34, 45.30, blue, findLocationOrThrow(MONTREAL), findLocationsOrThrow(CHICAGO, WASHINGTON, NEW_YORK_CITY)),
                new BoardSlot(-73.56 + POSITION_ADJUSTMENT, 40.40, blue, findLocationOrThrow(NEW_YORK_CITY), findLocationsOrThrow(MONTREAL, WASHINGTON, LONDON, MADRID)),
                new BoardSlot(-84.23, 33.45, blue, findLocationOrThrow(ATLANTA), findLocationsOrThrow(CHICAGO, WASHINGTON, MIAMI)),
                new BoardSlot(-77.02, 38.54, blue, findLocationOrThrow(WASHINGTON), findLocationsOrThrow(ATLANTA, MIAMI, NEW_YORK_CITY, MONTREAL)),
                new BoardSlot(-3.43, 40.23, blue, findLocationOrThrow(MADRID), findLocationsOrThrow(NEW_YORK_CITY, SAO_PAULO, ALGIERS, PARIS, LONDON)),
                new BoardSlot(-0.08, 51.30 + POSITION_ADJUSTMENT / 2, blue, findLocationOrThrow(LONDON), findLocationsOrThrow(NEW_YORK_CITY, MADRID, PARIS, ESSEN)),
                new BoardSlot(2.21, 48.51, blue, findLocationOrThrow(PARIS), findLocationsOrThrow(LONDON, MADRID, ALGIERS, MILAN, ESSEN)),
                new BoardSlot(6.47 + POSITION_ADJUSTMENT, 51.14, blue, findLocationOrThrow(ESSEN), findLocationsOrThrow(LONDON, PARIS, MILAN, SAINT_PETERSBURG)),
                new BoardSlot(9.11, 45.28, blue, findLocationOrThrow(MILAN), findLocationsOrThrow(ESSEN, PARIS, ISTANBUL)),
                new BoardSlot(30.18, 59.57, blue, findLocationOrThrow(SAINT_PETERSBURG), findLocationsOrThrow(ESSEN, ISTANBUL, MOSCOW))
        );
    }

    private List<BoardSlot> createYellow() throws NoSuchElementException {
        final Plague yellow = findPlagueOrThrow(PlagueCode.YELLOW);
        return List.of(
                new BoardSlot(-118.15, 34.03, yellow, findLocationOrThrow(LOS_ANGELES), findLocationsOrThrow(SAN_FRANCISCO, SYDNEY, MEXICO_CITY, CHICAGO)),
                new BoardSlot(-99.08, 19.26, yellow, findLocationOrThrow(MEXICO_CITY), findLocationsOrThrow(LOS_ANGELES, LIMA, BOGOTA, MIAMI, CHICAGO)),
                new BoardSlot(-80.13, 25.47, yellow, findLocationOrThrow(MIAMI), findLocationsOrThrow(MEXICO_CITY, BOGOTA, WASHINGTON, ATLANTA)),
                new BoardSlot(-74.05, 4.36, yellow, findLocationOrThrow(BOGOTA), findLocationsOrThrow(MEXICO_CITY, LIMA, BUENOS_AIRES, SAO_PAULO, MIAMI)),
                new BoardSlot(-77.02, -12.03, yellow, findLocationOrThrow(LIMA), findLocationsOrThrow(MEXICO_CITY, SANTIAGO, BOGOTA)),
                new BoardSlot(-70.40, -33.27, yellow, findLocationOrThrow(SANTIAGO), findLocationsOrThrow(LIMA)),
                new BoardSlot(-58.23, -34.36, yellow, findLocationOrThrow(BUENOS_AIRES), findLocationsOrThrow(BOGOTA, SAO_PAULO)),
                new BoardSlot(-46.38, -23.33, yellow, findLocationOrThrow(SAO_PAULO), findLocationsOrThrow(BOGOTA, BUENOS_AIRES, LAGOS, MADRID)),
                new BoardSlot(3.24, 6.27, yellow, findLocationOrThrow(LAGOS), findLocationsOrThrow(SAO_PAULO, KINSHASA, KHARTOUM)),
                new BoardSlot(15.19, -4.20, yellow, findLocationOrThrow(KINSHASA), findLocationsOrThrow(LAGOS, JOHANNESBURG, KHARTOUM)),
                new BoardSlot(28.03, -26.12, yellow, findLocationOrThrow(JOHANNESBURG), findLocationsOrThrow(KINSHASA, KHARTOUM)),
                new BoardSlot(32.32, 15.38, yellow, findLocationOrThrow(KHARTOUM), findLocationsOrThrow(LAGOS, KINSHASA, JOHANNESBURG, CAIRO))
        );
    }

    private List<BoardSlot> createBlack() throws NoSuchElementException {
        final Plague black = findPlagueOrThrow(PlagueCode.BLACK);
        return List.of(
                new BoardSlot(3.13, 36.46, black, findLocationOrThrow(ALGIERS), findLocationsOrThrow(MADRID, PARIS, ISTANBUL, CAIRO)),
                new BoardSlot(31.14, 30.03, black, findLocationOrThrow(CAIRO), findLocationsOrThrow(ALGIERS, ISTANBUL, BAGHDAD, RIYADH, KHARTOUM)),
                new BoardSlot(28.57, 41.01, black, findLocationOrThrow(ISTANBUL), findLocationsOrThrow(ALGIERS, CAIRO, BAGHDAD, MOSCOW, SAINT_PETERSBURG, MILAN)),
                new BoardSlot(37.37, 55.45, black, findLocationOrThrow(MOSCOW), findLocationsOrThrow(SAINT_PETERSBURG, ISTANBUL, TEHRAN)),
                new BoardSlot(51.25, 35.42, black, findLocationOrThrow(TEHRAN), findLocationsOrThrow(MOSCOW, BAGHDAD, KARACHI, NEW_DELHI)),
                new BoardSlot(44.26, 33.20, black, findLocationOrThrow(BAGHDAD), findLocationsOrThrow(ISTANBUL, CAIRO, RIYADH, KARACHI, TEHRAN)),
                new BoardSlot(46.43, 24.38, black, findLocationOrThrow(RIYADH), findLocationsOrThrow(CAIRO, BAGHDAD, KARACHI)),
                new BoardSlot(67.01, 24.52, black, findLocationOrThrow(KARACHI), findLocationsOrThrow(RIYADH, BAGHDAD, TEHRAN, NEW_DELHI, MUMBAI)),
                new BoardSlot(77.13, 28.37, black, findLocationOrThrow(NEW_DELHI), findLocationsOrThrow(TEHRAN, KARACHI, MUMBAI, CHENNAI, KOLKATA)),
                new BoardSlot(72.50, 18.59, black, findLocationOrThrow(MUMBAI), findLocationsOrThrow(KARACHI, NEW_DELHI, CHENNAI)),
                new BoardSlot(80.16, 13.05, black, findLocationOrThrow(CHENNAI), findLocationsOrThrow(MUMBAI, NEW_DELHI, KOLKATA, BANGKOK, JAKARTA)),
                new BoardSlot(88.22, 22.34, black, findLocationOrThrow(KOLKATA), findLocationsOrThrow(NEW_DELHI, CHENNAI, BANGKOK, HONG_KONG))
        );
    }

    private List<BoardSlot> createRed() throws NoSuchElementException {
        final Plague red = findPlagueOrThrow(PlagueCode.RED);
        return List.of(
                new BoardSlot(106.38, -6.12, red, findLocationOrThrow(JAKARTA), findLocationsOrThrow(CHENNAI, BANGKOK, HO_CHI_MINH_CITY, SYDNEY)),
                new BoardSlot(151.13, -33.52, red, findLocationOrThrow(SYDNEY), findLocationsOrThrow(JAKARTA, MANILA, LOS_ANGELES)),
                new BoardSlot(106.41 + POSITION_ADJUSTMENT, 10.46 - POSITION_ADJUSTMENT, red, findLocationOrThrow(HO_CHI_MINH_CITY), findLocationsOrThrow(JAKARTA, BANGKOK, HONG_KONG, MANILA)),
                new BoardSlot(100.28, 13.45, red, findLocationOrThrow(BANGKOK), findLocationsOrThrow(KOLKATA, CHENNAI, JAKARTA, HO_CHI_MINH_CITY, HONG_KONG)),
                new BoardSlot(114.10, 22.17, red, findLocationOrThrow(HONG_KONG), findLocationsOrThrow(KOLKATA, BANGKOK, HO_CHI_MINH_CITY, MANILA, TAIPEI, SHANGHAI)),
                new BoardSlot(120.58, 14.35, red, findLocationOrThrow(MANILA), findLocationsOrThrow(SYDNEY, SAN_FRANCISCO, TAIPEI, HONG_KONG, HO_CHI_MINH_CITY)),
                new BoardSlot(121.38, 25.02, red, findLocationOrThrow(TAIPEI), findLocationsOrThrow(OSAKA, SHANGHAI, HONG_KONG, MANILA)),
                new BoardSlot(135.30, 34.42 - POSITION_ADJUSTMENT, red, findLocationOrThrow(OSAKA), findLocationsOrThrow(TAIPEI, TOKYO)),
                new BoardSlot(139.42, 35.41, red, findLocationOrThrow(TOKYO), findLocationsOrThrow(OSAKA, SAN_FRANCISCO, SEOUL, SHANGHAI)),
                new BoardSlot(126.59, 37.34, red, findLocationOrThrow(SEOUL), findLocationsOrThrow(TOKYO, BEIJING, SHANGHAI)),
                new BoardSlot(116.23, 39.55, red, findLocationOrThrow(BEIJING), findLocationsOrThrow(SEOUL, SHANGHAI)),
                new BoardSlot(121.30, 31.12, red, findLocationOrThrow(SHANGHAI), findLocationsOrThrow(BEIJING, SEOUL, TOKYO, TAIPEI, HONG_KONG))
        );
    }

    @Override
    protected BoardSettings createBoardSettings() {
        return new BoardSettings(
                125000,
                1,
                5,
                3,
                3,
                0,
                0,
                -180,
                180,
                -85,
                85
        );
    }
}
