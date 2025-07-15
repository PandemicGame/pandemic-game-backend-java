package game.pandemic.game.plague;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlagueFactory {
    public List<Plague> createAllPlagues() {
        return List.of(
                new Plague(PlagueCode.BLUE.getCode(), "Blue", new PlagueColor(0, 0, 255)),
                new Plague(PlagueCode.YELLOW.getCode(), "Yellow", new PlagueColor(255, 255, 0)),
                new Plague(PlagueCode.BLACK.getCode(), "Black", new PlagueColor(128, 128, 128)),
                new Plague(PlagueCode.RED.getCode(), "Red", new PlagueColor(255, 0, 0))
        );
    }
}
