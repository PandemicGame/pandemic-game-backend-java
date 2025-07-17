package game.pandemic.game.role;

import game.pandemic.util.Color;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleFactory {
    public List<Role> createAllRoles() {
        return List.of(
                new Role("Dispatcher", new Color(204, 0, 153)),
                new Role("Operations Expert", new Color(51, 204, 51)),
                new Role("Scientist", new Color(222, 222, 222)),
                new Role("Medic", new Color(255, 102, 0)),
                new Role("Researcher", new Color(153, 102, 51))
        );
    }
}
