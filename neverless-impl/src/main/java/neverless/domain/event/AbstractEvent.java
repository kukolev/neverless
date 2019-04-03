package neverless.domain.event;

import neverless.dto.event.EventType;

public abstract class AbstractEvent {

    public abstract EventType getEventType();

    public boolean displayable() {
        return false;
    }
}
