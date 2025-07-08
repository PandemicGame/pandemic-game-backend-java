package game.pandemic.lobby;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lobby implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @JsonView(JacksonView.Read.class)
    private String name;
    @OneToOne
    @JsonView(JacksonView.Read.class)
    private UserLobbyMember owner;
    @Getter
    @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(JacksonView.Read.class)
    private Set<LobbyMember> members;

    public Lobby(final String name, final UserLobbyMember owner) {
        this.name = name;
        this.owner = owner;
        this.members = new HashSet<>();
        addMember(owner);
    }

    public boolean containsMember(final LobbyMember member) {
        return this.members.contains(member);
    }

    public void addMember(final LobbyMember member) {
        this.members.add(member);
    }
}
