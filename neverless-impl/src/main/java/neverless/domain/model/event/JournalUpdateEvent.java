package neverless.domain.model.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.quest.QuestState;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class JournalUpdateEvent extends AbstractEvent {

    private String questTitle;
    private QuestState state;
}
