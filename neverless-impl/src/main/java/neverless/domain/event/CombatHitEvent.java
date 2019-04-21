package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CombatHitEvent extends AbstractEvent {

    private String defenderId;
    private Integer damage;

    @Override
    public String toString() {
        return String.format("%s gains damage: %s", defenderId, damage);
    }

    @Override
    public boolean displayable() {
        return true;
    }
}
