package game.pandemic.game.board.type;

import game.pandemic.game.board.location.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Location startingLocation;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BoardSlot> slots;

    public BoardType(final String name, final Location startingLocation, final List<BoardSlot> slots) {
        this.name = name;
        this.startingLocation = startingLocation;
        this.slots = slots;
    }

    public Optional<BoardSlot> findBoardSlotForLocation(final Location location) {
        return this.slots.stream()
                .filter(slot -> slot.hasLocation(location))
                .findFirst();
    }

    public List<BoardSlot> getConnectionsForBoardSlot(final BoardSlot boardSlot) {
        final List<Location> connectedLocations = boardSlot.getConnectedLocations();
        return this.slots.stream()
                .filter(slot -> connectedLocations.contains(slot.getLocation()))
                .toList();
    }
}
