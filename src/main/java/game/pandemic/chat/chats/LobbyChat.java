package game.pandemic.chat.chats;

import game.pandemic.lobby.Lobby;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LobbyChat extends Chat {
    @OneToOne
    private Lobby lobby;

    public LobbyChat(final Lobby lobby) {
        super(new HashSet<>(lobby.getMembers()));
        this.lobby = lobby;
    }
}
