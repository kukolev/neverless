package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Direction;
import neverless.dto.event.EventType;

@Data
@Accessors(chain = true)
public class MapGoEvent extends AbstractEvent{

    private Direction direction;

    @Override
    public EventType getEventType() {
        return EventType.MAP_GO_EVENT;
    }
}
