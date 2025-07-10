package game.pandemic.lobby.member;

import game.pandemic.user.User;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class UserLobbyMember extends LobbyMember implements IWebSocketAuthenticationObject {
    @ManyToOne
    private User user;

    public UserLobbyMember(final User user) {
        super(user.getName());
        this.user = user;
    }
}
