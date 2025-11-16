package game.pandemic.game.effect;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.Game;
import game.pandemic.game.events.GameEvent;
import game.pandemic.game.player.Player;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Effect implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @JsonView(JacksonView.Read.class)
    private Long id;
    @ManyToOne
    protected Player executingPlayer;
    @ManyToOne
    protected Player targetPlayer;
    @Setter
    private boolean isApproved;
    @Setter
    private boolean isRejected;

    protected Effect(final Player executingPlayer, final Player targetPlayer) {
        this.executingPlayer = executingPlayer;
        this.targetPlayer = targetPlayer;
        this.isApproved = false;
        this.isRejected = false;
    }

    public abstract List<GameEvent> createEvents();

    public abstract boolean requiresApproval();

    public boolean isAvailable(final Game game) {
        return !requiresApproval() || this.isApproved;
    }
}
