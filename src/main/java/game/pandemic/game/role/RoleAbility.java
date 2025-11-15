package game.pandemic.game.role;

import game.pandemic.game.action.IGeneralAction;
import game.pandemic.game.action.IRoleAction;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoleAbility {
    private List<String> additionalRoleActions;
    private List<String> removedGeneralActions;

    public RoleAbility(final List<Class<? extends IRoleAction>> additionalRoleActions,
                       final List<Class<? extends IGeneralAction>> removedGeneralActions) {
        this.additionalRoleActions = transformClassListToStringList(additionalRoleActions);
        this.removedGeneralActions = transformClassListToStringList(removedGeneralActions);
    }

    private <T> List<String> transformClassListToStringList(final List<Class<? extends T>> classList) {
        return classList.stream()
                .map(Class::getName)
                .toList();
    }
}
