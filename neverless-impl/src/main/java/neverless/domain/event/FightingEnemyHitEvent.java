package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.dto.event.EventType;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FightingEnemyHitEvent extends AbstractEvent {

    private String enemyId;
    private Integer damage;

    @Override
    public EventType getEventType() {
        return EventType.FIGHTING_ENEMY_HIT_EVENT;
    }
}
