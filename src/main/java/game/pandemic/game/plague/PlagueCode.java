package game.pandemic.game.plague;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlagueCode {
    BLACK("BLACK"),
    BLUE("BLUE"),
    RED("RED"),
    YELLOW("YELLOW");

    private final String code;
}
