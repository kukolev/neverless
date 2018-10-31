package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.event.EventType;

import static neverless.dto.screendata.event.EventType.PORTAL_ENTER_EVENT;

@Data
@Accessors(chain = true)
public class PortalEnterEvent extends AbstractEvent {

    @Override
    public EventType getEventType() {
        return PORTAL_ENTER_EVENT;
    }
}
