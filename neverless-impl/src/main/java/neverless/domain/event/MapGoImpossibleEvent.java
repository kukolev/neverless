package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.event.EventType;

@Data
@Accessors(chain = true)
public class MapGoImpossibleEvent extends AbstractEvent {

    private String id;

    @Override
    public EventType getEventType() {
        return EventType.MAP_GO_IMPOSSIBLE_EVENT;
    }


}
