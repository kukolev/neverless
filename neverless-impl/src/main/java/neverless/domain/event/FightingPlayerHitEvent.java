package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.dto.event.EventType;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FightingPlayerHitEvent extends AbstractEvent {

    private String enemyId;
    private Integer damage;

    @Override
    public EventType getEventType() {
        return EventType.FIGHTING_PLAYER_HIT_EVENT;
    }

    @Override
    public String toString() {
        return String.format("Enemy %s hits: %s", enemyId, damage);
    }

    @Override
    public boolean displayable() {
        return true;
    }
}
