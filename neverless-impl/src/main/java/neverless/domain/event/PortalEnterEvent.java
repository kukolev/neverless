package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.dto.event.EventType;

import static neverless.dto.event.EventType.PORTAL_ENTER_EVENT;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PortalEnterEvent extends AbstractEvent {

    @Override
    public EventType getEventType() {
        return PORTAL_ENTER_EVENT;
    }
}
