package game.pandemic.lobby.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import game.pandemic.user.User;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class UserLobbyMember extends LobbyMember implements IWebSocketAuthenticationObject {
    @ManyToOne
    private User user;
    @Setter
    @Column(unique = true)
    @JsonIgnore
    private UUID accessToken;

    public UserLobbyMember(final User user) {
        super(user.getName());
        this.user = user;
    }
}
