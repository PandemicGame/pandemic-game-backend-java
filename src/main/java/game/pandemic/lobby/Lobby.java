package game.pandemic.lobby;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.chat.chats.LobbyChat;
import game.pandemic.event.IEventContext;
import game.pandemic.game.GameOptions;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.events.CreateLobbyEvent;
import game.pandemic.lobby.events.LobbyEvent;
import game.pandemic.lobby.member.LobbyMember;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Lobby implements IWebSocketData, IEventContext<Lobby, LobbyEvent> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @Setter
    @JsonView(JacksonView.Read.class)
    private String name;
    @Setter
    @OneToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private UserLobbyMember owner;
    @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonView(JacksonView.Read.class)
    private Set<LobbyMember> members;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private LobbyChat chat;
    @OneToOne(cascade = CascadeType.ALL)
    private CreateLobbyEvent creationEvent;
    @Setter
    @JsonView(JacksonView.Read.class)
    private boolean isClosed;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JsonView(JacksonView.Read.class)
    private GameOptions gameOptions;

    public Lobby(final CreateLobbyEvent creationEvent) {
        initialize();
        this.chat = new LobbyChat(this);
        this.creationEvent = creationEvent;
        this.creationEvent.apply(this);
    }

    private void initialize() {
        this.members = new HashSet<>();
        this.isClosed = false;
    }

    @Override
    public void processEvent(final LobbyEvent event) {
        this.creationEvent.appendEvent(event);
        event.apply(this);
    }

    @Override
    public void reset() {
        initialize();
        this.creationEvent.apply(this);
    }

    @Override
    public void restore() {
        this.creationEvent.applyAll(this);
    }

    public boolean isOwner(final LobbyMember lobbyMember) {
        if (lobbyMember instanceof UserLobbyMember userLobbyMember) {
            return isOwner(userLobbyMember);
        }
        return false;
    }

    public boolean isOwner(final UserLobbyMember lobbyMember) {
        return this.owner.equals(lobbyMember);
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

    public Set<LobbyMember> getMembers() {
        return Collections.unmodifiableSet(this.members);
    }
}
