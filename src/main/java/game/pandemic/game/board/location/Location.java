package game.pandemic.game.board.location;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location {
    @Id
    @EqualsAndHashCode.Include
    private String code;
    private String name;
    private String information;
}
