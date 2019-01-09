package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.dto.event.EventType;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DialogSelectPhraseEvent extends AbstractEvent {

    private Integer phraseNumber;

    @Override
    public EventType getEventType() {
        return EventType.DIALOG_SELECT_PHRASE_EVENT;
    }
}
