package game.pandemic.game.board.type;

import game.pandemic.game.board.location.Location;
import game.pandemic.game.plague.Plague;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BoardSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private double xCoordinate;
    private double yCoordinate;
    @ManyToOne
    private Plague plague;
    @ManyToOne
    private Location location;
    @ManyToMany
    private List<Location> connectedLocations;

    public BoardSlot(final double xCoordinate, final double yCoordinate, final Plague plague, final Location location, final List<Location> connectedLocations) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.plague = plague;
        this.location = location;
        this.connectedLocations = connectedLocations;
    }

    public boolean hasLocation(final Location location) {
        return this.location.equals(location);
    }

    public String getName() {
        return this.location.getName();
    }
}
