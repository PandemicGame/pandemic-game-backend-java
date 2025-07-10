package game.pandemic.lobby;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.chat.chats.LobbyChat;
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
    @Getter
    @JsonView(JacksonView.Read.class)
    private String name;
    @OneToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private UserLobbyMember owner;
    @Getter
    @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    private Set<LobbyMember> members;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private LobbyChat chat;

    public Lobby(final String name, final UserLobbyMember owner) {
        this.name = name;
        this.owner = owner;
        this.members = new HashSet<>();
        this.chat = new LobbyChat(this);
        addMember(owner);
    }

    public boolean containsMember(final LobbyMember member) {
        return this.members.contains(member);
    }

    public void addMember(final LobbyMember member) {
        member.setLobby(this);
        this.members.add(member);
        this.chat.addMember(member);
    }

    public void removeMember(final LobbyMember member) {
        member.setLobby(null);
        this.members.remove(member);
        this.chat.removeMember(member);
    }
}
