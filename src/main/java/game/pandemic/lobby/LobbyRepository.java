package game.pandemic.lobby;

import game.pandemic.lobby.member.LobbyMember;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {
    Optional<Lobby> findLobbyByMembersContaining(final LobbyMember lobbyMember);
    Optional<Lobby> findByIdAndIsClosedFalse(final Long id);
    List<Lobby> findAllByIsClosedFalse();
    @Modifying
    @Transactional
    @Query("UPDATE Lobby l SET l.isClosed = true")
    int closeAll();
}
