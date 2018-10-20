package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DialogSelectPhraseEvent extends AbstractEvent {

    private Integer phraseNumber;

    @Override
    public EventType getType() {
        return EventType.DIALOG_PHRASE_SELECTED_EVENT;
    }
}
