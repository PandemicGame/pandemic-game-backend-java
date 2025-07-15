package game.pandemic.game.plague;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlagueService {
    private final PlagueFactory plagueFactory;
    private final PlagueRepository plagueRepository;

    public void createAllPlagues() {
        if (!this.plagueRepository.existsBy()) {
            this.plagueRepository.saveAll(this.plagueFactory.createAllPlagues());
        }
    }
}
