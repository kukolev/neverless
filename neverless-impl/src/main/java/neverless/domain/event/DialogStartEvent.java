package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.dto.event.EventType;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DialogStartEvent extends AbstractEvent{

    private String npcName;
    private Integer npcX;
    private Integer npcY;

    @Override
    public EventType getEventType() {
        return EventType.DIALOG_START_EVENT;
    }
}
