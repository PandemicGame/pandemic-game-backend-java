package game.pandemic.game.action.effect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionEffectRepository extends JpaRepository<ActionEffect, Long> {
}
