package game.pandemic.game.board.location;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Component
public class LocationFactory {
    public List<Location> createAllLocations() {
        return Stream.of(
                createNorthAmerica(),
                createSouthAmerica(),
                createAfrica(),
                createEurope(),
                createAsia(),
                createOceania()
        ).flatMap(Collection::stream).toList();
    }

    private List<Location> createNorthAmerica() {
        return List.of(
                new Location(LocationCode.SAN_FRANCISCO.getCode(), "San Francisco", ""),
                new Location(LocationCode.CHICAGO.getCode(), "Chicago", ""),
                new Location(LocationCode.MONTREAL.getCode(), "Montreal", ""),
                new Location(LocationCode.NEW_YORK_CITY.getCode(), "New York City", ""),
                new Location(LocationCode.WASHINGTON.getCode(), "Washington, D.C.", ""),
                new Location(LocationCode.ATLANTA.getCode(), "Atlanta", ""),
                new Location(LocationCode.MIAMI.getCode(), "Miami", ""),
                new Location(LocationCode.LOS_ANGELES.getCode(), "Los Angeles", ""),
                new Location(LocationCode.MEXICO_CITY.getCode(), "Mexico City", "")
        );
    }

    private List<Location> createSouthAmerica() {
        return List.of(
                new Location(LocationCode.BOGOTA.getCode(), "Bogota", ""),
                new Location(LocationCode.LIMA.getCode(), "Lima", ""),
                new Location(LocationCode.SANTIAGO.getCode(), "Santiago", ""),
                new Location(LocationCode.SAO_PAULO.getCode(), "São Paulo", ""),
                new Location(LocationCode.BUENOS_AIRES.getCode(), "Buenos Aires", "")
        );
    }

    private List<Location> createEurope() {
        return List.of(
                new Location(LocationCode.MADRID.getCode(), "Madrid", ""),
                new Location(LocationCode.LONDON.getCode(), "London", ""),
                new Location(LocationCode.PARIS.getCode(), "Paris", ""),
                new Location(LocationCode.ESSEN.getCode(), "Essen", ""),
                new Location(LocationCode.MILAN.getCode(), "Milan", ""),
                new Location(LocationCode.SAINT_PETERSBURG.getCode(), "Saint Petersburg", ""),
                new Location(LocationCode.MOSCOW.getCode(), "Moscow", ""),
                new Location(LocationCode.ISTANBUL.getCode(), "Istanbul", "")
        );
    }

    private List<Location> createAfrica() {
        return List.of(
                new Location(LocationCode.ALGIERS.getCode(), "Algiers", ""),
                new Location(LocationCode.CAIRO.getCode(), "Cairo", ""),
                new Location(LocationCode.LAGOS.getCode(), "Lagos", ""),
                new Location(LocationCode.KHARTOUM.getCode(), "Khartoum", ""),
                new Location(LocationCode.KINSHASA.getCode(), "Kinshasa", ""),
                new Location(LocationCode.JOHANNESBURG.getCode(), "Johannesburg", "")
        );
    }

    private List<Location> createAsia() {
        return List.of(
                new Location(LocationCode.BAGHDAD.getCode(), "Baghdad", ""),
                new Location(LocationCode.RIYADH.getCode(), "Riyadh", ""),
                new Location(LocationCode.TEHRAN.getCode(), "Tehran", ""),
                new Location(LocationCode.KARACHI.getCode(), "Karachi", ""),
                new Location(LocationCode.NEW_DELHI.getCode(), "New Delhi", ""),
                new Location(LocationCode.MUMBAI.getCode(), "Mumbai", ""),
                new Location(LocationCode.CHENNAI.getCode(), "Chennai", ""),
                new Location(LocationCode.KOLKATA.getCode(), "Kolkata", ""),
                new Location(LocationCode.BANGKOK.getCode(), "Bangkok", ""),
                new Location(LocationCode.JAKARTA.getCode(), "Jakarta", ""),
                new Location(LocationCode.HO_CHI_MINH_CITY.getCode(), "Ho Chi Minh City", ""),
                new Location(LocationCode.MANILA.getCode(), "Manila", ""),
                new Location(LocationCode.HONG_KONG.getCode(), "Hong Kong", ""),
                new Location(LocationCode.TAIPEI.getCode(), "Taipei", ""),
                new Location(LocationCode.SHANGHAI.getCode(), "Shanghai", ""),
                new Location(LocationCode.BEIJING.getCode(), "Beijing", ""),
                new Location(LocationCode.SEOUL.getCode(), "Seoul", ""),
                new Location(LocationCode.TOKYO.getCode(), "Tokyo", ""),
                new Location(LocationCode.OSAKA.getCode(), "Osaka", "")
        );
    }

    private List<Location> createOceania() {
        return List.of(
                new Location(LocationCode.SYDNEY.getCode(), "Sydney", "")
        );
    }
}
