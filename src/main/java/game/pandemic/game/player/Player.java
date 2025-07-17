package game.pandemic.game.player;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.board.Field;
import game.pandemic.game.role.Role;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Player implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @OneToOne
    private LobbyMember lobbyMember;
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Field currentField;
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    private Role role;

    public Player(final LobbyMember lobbyMember, final Field startingField, final Role role) {
        this.lobbyMember = lobbyMember;
        this.currentField = startingField;
        this.role = role;
    }

    public boolean containsLobbyMember(final LobbyMember lobbyMember) {
        return this.lobbyMember.equals(lobbyMember);
    }
}
