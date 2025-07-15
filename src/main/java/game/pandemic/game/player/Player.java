package game.pandemic.game.player;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @OneToOne
    private LobbyMember lobbyMember;

    public Player(final LobbyMember lobbyMember) {
        this.lobbyMember = lobbyMember;
    }

    public boolean containsLobbyMember(final LobbyMember lobbyMember) {
        return this.lobbyMember.equals(lobbyMember);
    }
}
