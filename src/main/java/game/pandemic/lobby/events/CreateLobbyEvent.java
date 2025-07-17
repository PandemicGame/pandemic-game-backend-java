package game.pandemic.lobby.events;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.GameOptions;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.Lobby;
import game.pandemic.lobby.member.UserLobbyMember;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateLobbyEvent extends LobbyEvent {
    @JsonView(JacksonView.Read.class)
    private String name;
    @OneToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private UserLobbyMember owner;
    @OneToOne
    private GameOptions gameOptions;

    @Override
    public void apply(final Lobby lobby) {
        lobby.setName(this.name);
        lobby.setOwner(this.owner);
        lobby.addMember(this.owner);
        lobby.setGameOptions(this.gameOptions);
    }

    public Lobby createLobby() {
        return new Lobby(this);
    }
}
