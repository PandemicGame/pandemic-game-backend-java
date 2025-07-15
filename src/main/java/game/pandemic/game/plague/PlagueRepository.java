package game.pandemic.game.plague;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlagueRepository extends JpaRepository<Plague, String> {
    boolean existsBy();
    Optional<Plague> findByCode(final String code);
    default Optional<Plague> findByCode(final PlagueCode plagueCode) {
        return findByCode(plagueCode.getCode());
    }
}
