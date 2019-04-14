package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FightingPlayerHitEvent extends AbstractEvent {

    private String enemyId;
    private Integer damage;

    @Override
    public String toString() {
        return String.format("Enemy %s gains damage: %s", enemyId, damage);
    }

    @Override
    public boolean displayable() {
        return true;
    }
}
