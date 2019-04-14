package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FightingEnemyKilledEvent extends AbstractEvent {

    private String enemyId;

    @Override
    public String toString() {
        return String.format("Enemy %s is dead", enemyId);
    }

    @Override
    public boolean displayable() {
        return true;
    }
}