package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.event.EventType;

@Data
@Accessors(chain = true)
public class FightingPlayerMissEvent extends AbstractEvent {

    private String enemyId;

    @Override
    public EventType getEventType() {
        return EventType.FIGHTING_PLAYER_MISS_EVENT;
    }
}