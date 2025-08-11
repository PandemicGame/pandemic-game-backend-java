package game.pandemic.game.events;

import game.pandemic.event.Event;
import game.pandemic.game.Game;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GameEvent extends Event<Game, GameEvent> {
}
