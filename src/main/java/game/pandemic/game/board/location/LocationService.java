package game.pandemic.game.board.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationFactory locationFactory;
    private final LocationRepository locationRepository;

    public void createAllLocations() {
        if (!this.locationRepository.existsBy()) {
            this.locationRepository.saveAll(this.locationFactory.createAllLocations());
        }
    }
}
