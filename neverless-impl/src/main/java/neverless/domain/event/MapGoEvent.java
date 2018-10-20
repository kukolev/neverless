package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.command.Direction;

@Data
@Accessors(chain = true)
public class MapGoEvent extends AbstractEvent{

    private Direction direction;

    @Override
    public EventType getType() {
        return EventType.MAP_GO_EVENT;
    }
}
