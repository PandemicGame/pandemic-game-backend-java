package game.pandemic.game.board.type.option;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTypeOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
