package game.pandemic.game.board.type;

import game.pandemic.game.board.location.Location;
import game.pandemic.game.plague.Plague;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int xCoordinate;
    private int yCoordinate;
    @ManyToOne
    private Plague plague;
    @ManyToOne
    private Location location;
    @ManyToMany
    private List<Location> connectedLocations;

    public BoardSlot(final int xCoordinate, final int yCoordinate, final Plague plague, final Location location, final List<Location> connectedLocations) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.plague = plague;
        this.location = location;
        this.connectedLocations = connectedLocations;
    }
}
