package game.pandemic.game;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class GameOptions implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @ManyToMany
    @JsonView(JacksonView.Read.class)
    private List<BoardType> availableBoardTypes;
    @Setter
    @JsonView({JacksonView.Read.class, JacksonView.Update.class})
    private Long selectedBoardTypeId;
}
