package game.pandemic.game.player;

import game.pandemic.lobby.member.LobbyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByLobbyMember(final LobbyMember lobbyMember);
}
