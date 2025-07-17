package game.pandemic.game.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleFactory roleFactory;
    private final RoleRepository roleRepository;

    public void createAllRoles() {
        if (!this.roleRepository.existsBy()) {
            this.roleRepository.saveAll(this.roleFactory.createAllRoles());
        }
    }
}
