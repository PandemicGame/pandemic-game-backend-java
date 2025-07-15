package game.pandemic.game.board.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    boolean existsBy();
    Optional<Location> findByCode(final String code);
    default Optional<Location> findByCode(final LocationCode locationCode) {
        return findByCode(locationCode.getCode());
    }
    List<Location> findAllByCodeIn(final List<String> codes);
    default List<Location> findAllByCodeIn(final LocationCode... locationCodes) {
        final List<String> codesAsStrings = Arrays.stream(locationCodes)
                .map(LocationCode::getCode)
                .toList();
        return findAllByCodeIn(codesAsStrings);
    }
}
