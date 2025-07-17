package game.pandemic.game.plague;

import game.pandemic.util.Color;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlagueFactory {
    public List<Plague> createAllPlagues() {
        return List.of(
                new Plague(PlagueCode.BLUE.getCode(), "Blue", new Color(0, 0, 255)),
                new Plague(PlagueCode.YELLOW.getCode(), "Yellow", new Color(255, 255, 0)),
                new Plague(PlagueCode.BLACK.getCode(), "Black", new Color(128, 128, 128)),
                new Plague(PlagueCode.RED.getCode(), "Red", new Color(255, 0, 0))
        );
    }
}
