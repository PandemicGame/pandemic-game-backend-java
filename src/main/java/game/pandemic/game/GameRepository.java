package game.pandemic.game;

import game.pandemic.game.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByPlayersInTurnOrderContaining(final Player player);
}
