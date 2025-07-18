package game.pandemic.game.board.location;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LocationCode {
    ALGIERS("ALG"),
    ATLANTA("ATL"),
    BAGHDAD("BAG"),
    BANGKOK("BAN"),
    BEIJING("BEI"),
    BOGOTA("BOG"),
    BORKUM_CORE("BOR_C"),
    BORKUM_REEDE("BOR_R"),
    BORKUM_OSTLAND("BOR_O"),
    BUENOS_AIRES("BA"),
    CAIRO("CAI"),
    CHENNAI("CHE"),
    CHICAGO("CHI"),
    ESSEN("ESS"),
    HO_CHI_MINH_CITY("HCM"),
    HONG_KONG("HK"),
    ISTANBUL("IST"),
    JAKARTA("JAK"),
    JOHANNESBURG("JB"),
    KARACHI("KAR"),
    KHARTOUM("KHA"),
    KINSHASA("KIN"),
    KOLKATA("KOL"),
    LAGOS("LAG"),
    LIMA("LIM"),
    LONDON("LON"),
    LOS_ANGELES("LA"),
    MADRID("MAD"),
    MANILA("MAN"),
    MEXICO_CITY("MEX"),
    MIAMI("MIA"),
    MILAN("MIL"),
    MONTREAL("MON"),
    MOSCOW("MOS"),
    MUMBAI("MUM"),
    NEW_DELHI("ND"),
    NEW_YORK_CITY("NYC"),
    OSAKA("OSA"),
    PARIS("PAR"),
    RIYADH("RIY"),
    SAINT_PETERSBURG("STP"),
    SAN_FRANCISCO("SF"),
    SANTIAGO("SAN"),
    SAO_PAULO("SP"),
    SEOUL("SEO"),
    SHANGHAI("SHA"),
    SYDNEY("SYD"),
    TAIPEI("TAI"),
    TEHRAN("TEH"),
    TOKYO("TOK"),
    WASHINGTON("WDC");

    private final String code;
}
