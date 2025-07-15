package game.pandemic.game;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.Lobby;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @OneToOne
    @JsonView(JacksonView.Read.class)
    private Lobby lobby;

    public Game(final Lobby lobby) {
        this.lobby = lobby;
        this.lobby.setGame(this);
    }
}
