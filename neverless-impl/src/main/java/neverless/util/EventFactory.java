package neverless.util;

import neverless.domain.event.*;
import neverless.dto.command.Direction;
import org.springframework.stereotype.Service;

@Service
public class EventFactory {

    public AbstractEvent createMapGoEvent(Direction direction) {
        return new MapGoEvent()
                .setDirection(direction);
    }

    public AbstractEvent createMapGoImpossibleEvent() {
        return new MapGoImpossibleEvent();
    }

    public AbstractEvent createDialogSelectPhraseEvent(Integer phraseNumber) {
        AbstractEvent event = new DialogSelectPhraseEvent()
                .setPhraseNumber(phraseNumber);
        return event;
    }

    public final String EVENT_PARAM_NPC_NAME = "npcName";

    public AbstractEvent createDialogStartEvent(String npcName) {
        AbstractEvent event = new DialogStartEvent()
                .setNpcName(npcName);
        return event;
    }
}
