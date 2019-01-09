package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.dto.event.EventType;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EnemyMoveEvent extends AbstractEvent {

    private String enemyId;

    @Override
    public EventType getEventType() {
        return EventType.ENEMY_MOVE;
    }
}
