package game.pandemic.lobby;

import game.pandemic.lobby.member.LobbyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {
    Optional<Lobby> findLobbyByMembersContaining(final LobbyMember lobbyMember);
}
