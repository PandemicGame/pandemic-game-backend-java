package game.pandemic.game;

import game.pandemic.game.plague.PlagueService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final PlagueService plagueService;

    @PostConstruct
    private void postConstruct() {
        this.plagueService.createAllPlagues();
    }
}
