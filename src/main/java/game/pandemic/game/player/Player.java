package game.pandemic.game.player;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.board.Field;
import game.pandemic.game.card.player.PlayerCard;
import game.pandemic.game.role.Role;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    @EqualsAndHashCode.Include
    private Long id;
    @OneToOne
    private LobbyMember lobbyMember;
    @Setter
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Field currentField;
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    private Role role;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JsonView(JacksonView.Read.class)
    private List<PlayerCard> handCards;

    public Player(final LobbyMember lobbyMember, final Field startingField, final Role role) {
        this.lobbyMember = lobbyMember;
        this.currentField = startingField;
        this.role = role;
        this.handCards = new LinkedList<>();
    }

    @JsonView(JacksonView.Read.class)
    public String getName() {
        return this.lobbyMember.getName();
    }

    public boolean containsLobbyMember(final LobbyMember lobbyMember) {
        return this.lobbyMember.equals(lobbyMember);
    }

    public void addHandCard(final PlayerCard card) {
        this.handCards.add(card);
    }
}
