package game.pandemic.game.turn;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.event.IEventContext;
import game.pandemic.game.Game;
import game.pandemic.game.events.GameEvent;
import game.pandemic.game.player.Player;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Turn implements IWebSocketData, IEventContext<Game, GameEvent> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Game game;
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Player player;

    public Turn(final Game game) {
        this.game = game;
        this.player = this.game.getCurrentPlayer();
    }

    @Override
    public void processEvent(final GameEvent event) {
        this.game.processEvent(event);
    }

    @Override
    public void reset() {
        this.game.reset();
    }

    @Override
    public void restore() {
        this.game.restore();
    }
}
