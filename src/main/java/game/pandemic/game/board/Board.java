package game.pandemic.game.board;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.board.type.BoardSlot;
import game.pandemic.game.board.type.BoardType;
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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @ManyToOne
    private BoardType type;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    private List<Field> fields;
    @OneToOne
    private Field startingField;

    public Board(final BoardType type) {
        this.type = type;
        this.fields = createFields(type);
        final BoardSlot startingSlot = type.findBoardSlotForLocation(type.getStartingLocation()).orElseThrow();
        this.startingField = findFieldForBoardSlot(startingSlot).orElseThrow();
    }

    private List<Field> createFields(final BoardType type) {
        return type.getSlots().stream()
                .map(slot -> new Field(this, slot))
                .toList();
    }

    public Optional<Field> findFieldForBoardSlot(final BoardSlot boardSlot) {
        return this.fields.stream()
                .filter(field -> field.hasBoardSlot(boardSlot))
                .findFirst();
    }

    public List<Field> getConnectionsForField(final Field field) {
        final List<BoardSlot> connections = this.type.getConnectionsForBoardSlot(field.getSlot());
        return this.fields.stream()
                .filter(f -> connections.contains(f.getSlot()))
                .toList();
    }

    public Set<Plague> getPlagues() {
        return this.type.getPlagues();
    }
}
