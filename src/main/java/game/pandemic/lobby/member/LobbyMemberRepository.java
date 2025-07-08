package game.pandemic.lobby.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyMemberRepository extends JpaRepository<LobbyMember, Long> {
}
