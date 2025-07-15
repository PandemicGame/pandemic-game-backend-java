package game.pandemic.game.board.type;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardTypeService {
    private final BoardTypeRepository boardTypeRepository;
    private final WorldMapBoardTypeFactory worldMapBoardTypeFactory;

    public void createAllBoardTypes() {
        if (!this.boardTypeRepository.existsBy()) {
            try {
                this.boardTypeRepository.save(this.worldMapBoardTypeFactory.createWorldMap());
            } catch (final BoardTypeCreationException e) {
                log.error("BoardType cannot be created because an exception occurred: " + e.getMessage());
            }
        }
    }
}
