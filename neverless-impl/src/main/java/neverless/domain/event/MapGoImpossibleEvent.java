package neverless.domain.event;

import neverless.dto.screendata.event.EventType;

public class MapGoImpossibleEvent extends AbstractEvent {

    @Override
    public EventType getEventType() {
        return EventType.MAP_GO_IMPOSSIBLE_EVENT;
    }
}
