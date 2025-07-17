package game.pandemic.game.board.type;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.board.location.Location;
import game.pandemic.game.plague.Plague;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardType implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @JsonView(JacksonView.Read.class)
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

    public Set<Plague> getPlagues() {
        return this.slots.stream()
                .map(BoardSlot::getPlague)
                .collect(Collectors.toSet());
    }
}
