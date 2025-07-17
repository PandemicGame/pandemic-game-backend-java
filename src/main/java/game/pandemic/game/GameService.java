package game.pandemic.game;

import game.pandemic.game.board.location.LocationService;
import game.pandemic.game.board.type.BoardTypeService;
import game.pandemic.game.plague.PlagueService;
import game.pandemic.game.role.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final BoardTypeService boardTypeService;
    private final LocationService locationService;
    private final PlagueService plagueService;
    private final RoleService roleService;

    @PostConstruct
    private void postConstruct() {
        this.plagueService.createAllPlagues();

        this.locationService.createAllLocations();
        this.boardTypeService.createAllBoardTypes();

        this.roleService.createAllRoles();
    }
}
