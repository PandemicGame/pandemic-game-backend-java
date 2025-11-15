package game.pandemic.game.card;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.JacksonView;
import game.pandemic.util.Color;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Card implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @JsonView(JacksonView.Read.class)
    private Long id;
    private String title;
    @Embedded
    private Color color;

    protected Card(final String title, final Color color) {
        this.title = title;
        this.color = color;
    }
}
