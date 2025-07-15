package game.pandemic.game.plague;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.IJsonTypeInfo;
import game.pandemic.jackson.JacksonView;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlagueColor implements IJsonTypeInfo {
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 255;

    public static boolean isValidValue(final int value) {
        return value >= MIN_VALUE && value <= MAX_VALUE;
    }

    public static boolean isInvalidValue(final int value) {
        return !isValidValue(value);
    }

    @JsonView(JacksonView.Read.class)
    private int r;
    @JsonView(JacksonView.Read.class)
    private int g;
    @JsonView(JacksonView.Read.class)
    private int b;

    public PlagueColor(final int r, final int g, final int b) throws IllegalArgumentException {
        if (isInvalidValue(r) || isInvalidValue(g) || isInvalidValue(b)) {
            throw new IllegalArgumentException();
        }

        this.r = r;
        this.g = g;
        this.b = b;
    }
}
