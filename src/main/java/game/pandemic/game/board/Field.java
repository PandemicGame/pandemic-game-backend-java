package game.pandemic.game.board;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.board.type.BoardSlot;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Field implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @ManyToOne
    private Board board;
    @ManyToOne
    private BoardSlot slot;

    public Field(final Board board, final BoardSlot slot) {
        this.board = board;
        this.slot = slot;
    }

    public boolean hasBoardSlot(final BoardSlot boardSlot) {
        return this.slot.equals(boardSlot);
    }

    @JsonView(JacksonView.Read.class)
    public String getName() {
        return this.slot.getName();
    }

    @JsonView(JacksonView.Read.class)
    public int getXCoordinate() {
        return this.slot.getXCoordinate();
    }

    @JsonView(JacksonView.Read.class)
    public int getYCoordinate() {
        return this.slot.getYCoordinate();
    }

    @JsonView(JacksonView.Read.class)
    public String getPlagueCode() {
        return this.slot.getPlague().getCode();
    }

    @JsonView(JacksonView.Read.class)
    public List<Long> getConnectionIds() {
        return this.board.getConnectionsForField(this).stream()
                .map(Field::getId)
                .toList();
    }
}
