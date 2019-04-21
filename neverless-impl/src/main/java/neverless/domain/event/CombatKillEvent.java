package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CombatKillEvent extends AbstractEvent {

    private String defenderId;

    @Override
    public String toString() {
        return String.format("%s is dead", defenderId);
    }

    @Override
    public boolean displayable() {
        return true;
    }
}