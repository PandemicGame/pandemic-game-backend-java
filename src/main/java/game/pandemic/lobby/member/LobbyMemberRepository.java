package game.pandemic.lobby.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyMemberRepository extends JpaRepository<LobbyMember, Long> {
    @Modifying
    @Query("UPDATE LobbyMember m SET m.lobby = null WHERE m.id = :id")
    void detachLobby(final Long id);
}
