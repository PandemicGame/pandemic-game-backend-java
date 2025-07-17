package game.pandemic.game.role;

import game.pandemic.lobby.member.LobbyMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LobbyMemberRoleAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private LobbyMember lobbyMember;
    @ManyToOne
    private Role role;

    public LobbyMemberRoleAssociation(final LobbyMember lobbyMember, final Role role) {
        this.lobbyMember = lobbyMember;
        this.role = role;
    }
}
