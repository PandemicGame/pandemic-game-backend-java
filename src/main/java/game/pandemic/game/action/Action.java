package game.pandemic.game.action;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.action.effect.ActionEffect;
import game.pandemic.game.player.Player;
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
public abstract class Action implements IAction, IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    protected Player executingPlayer;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    private List<ActionEffect> availableEffects;

    protected Action(final Player executingPlayer) {
        this.executingPlayer = executingPlayer;
        this.availableEffects = createAvailableEffects();
    }

    protected abstract List<ActionEffect> createAvailableEffects();
}
