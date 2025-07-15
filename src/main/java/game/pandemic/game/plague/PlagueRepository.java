package game.pandemic.game.plague;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlagueRepository extends JpaRepository<Plague, String> {
    boolean existsBy();
}
