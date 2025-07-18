package game.pandemic.game.board.type;

import game.pandemic.game.board.type.factories.BoardTypeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardTypeService {
    private final BoardTypeRepository boardTypeRepository;
    private final List<BoardTypeFactory> boardTypeFactories;

    public void createAllBoardTypes() {
        if (!this.boardTypeRepository.existsBy()) {
            for (final BoardTypeFactory boardTypeFactory : boardTypeFactories) {
                try {
                    this.boardTypeRepository.save(boardTypeFactory.createBoardType());
                } catch (final BoardTypeCreationException e) {
                    log.error("BoardType cannot be created because an exception occurred: " + e.getMessage());
                }
            }
        }
    }
}
