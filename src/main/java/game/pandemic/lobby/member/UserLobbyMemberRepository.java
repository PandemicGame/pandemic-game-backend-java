package game.pandemic.lobby.member;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLobbyMemberRepository extends JpaRepository<UserLobbyMember, Long>, IWebSocketAuthenticationObjectRepository<UserLobbyMember> {
}
