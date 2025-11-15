package game.pandemic.game.role;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.JacksonView;
import game.pandemic.util.Color;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Role implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @JsonView(JacksonView.Read.class)
    private String name;
    @Embedded
    @JsonView(JacksonView.Read.class)
    private Color color;
    @Embedded
    private RoleAbility ability;

    public Role(final String name, final Color color, final RoleAbility ability) {
        this.name = name;
        this.color = color;
        this.ability = ability;
    }
}
