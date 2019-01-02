package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.event.EventType;
import neverless.dto.quest.QuestState;

@Data
@Accessors(chain = true)
public class JournalUpdateEvent extends AbstractEvent {

    private String questTitle;
    private QuestState state;

    @Override
    public EventType getEventType() {
        return EventType.JOURNAL_UPDATED_EVENT;
    }
}
