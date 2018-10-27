package neverless.domain.event;

import neverless.dto.screendata.event.EventType;

public abstract class AbstractEvent {

    public abstract EventType getEventType();
}
