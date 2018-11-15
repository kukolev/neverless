package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.event.EventType;

@Data
@Accessors(chain = true)
public class FightingEnemyHitEvent extends AbstractEvent {

    private String enemyId;
    private Integer damage;

    @Override
    public EventType getEventType() {
        return EventType.FIGHTING_ENEMY_HIT_EVENT;
    }
}
