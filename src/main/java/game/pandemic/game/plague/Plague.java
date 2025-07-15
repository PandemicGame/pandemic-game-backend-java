package game.pandemic.game.plague;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.IJsonTypeInfo;
import game.pandemic.jackson.JacksonView;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Plague implements IJsonTypeInfo {
    @Id
    @JsonView(JacksonView.Read.class)
    @EqualsAndHashCode.Include
    private String code;
    @JsonView(JacksonView.Read.class)
    private String name;
    @Embedded
    @JsonView(JacksonView.Read.class)
    private PlagueColor color;
}
