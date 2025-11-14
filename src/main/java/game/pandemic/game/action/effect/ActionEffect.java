package game.pandemic.game.action.effect;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.events.GameEvent;
import game.pandemic.game.player.Player;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ActionEffect implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @ManyToOne
    protected Player executingPlayer;

    protected ActionEffect(final Player executingPlayer) {
        this.executingPlayer = executingPlayer;
    }

    public abstract List<GameEvent> createEvents();
}
