package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DialogStartEvent extends AbstractEvent{

    private String npcName;
    private Integer npcX;
    private Integer npcY;

    @Override
    public EventType getType() {
        return EventType.DIALOG_START_EVENT;
    }
}
