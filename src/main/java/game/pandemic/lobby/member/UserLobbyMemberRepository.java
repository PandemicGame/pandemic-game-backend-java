package game.pandemic.lobby.member;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLobbyMemberRepository extends JpaRepository<UserLobbyMember, Long>, IWebSocketAuthenticationObjectRepository<UserLobbyMember> {
    Optional<UserLobbyMember> findUserLobbyMemberByAccessToken(final UUID accessToken);
    default Optional<UserLobbyMember> findByAccessToken(final UUID accessToken) {
        return this.findUserLobbyMemberByAccessToken(accessToken);
    }
}
