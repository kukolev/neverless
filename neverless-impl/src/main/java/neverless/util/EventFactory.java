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

        return new DialogSelectPhraseEvent()
                .setPhraseNumber(phraseNumber);
    }

    public AbstractEvent createDialogStartEvent(String npcName, Integer npcX, Integer npcY) {
        return new DialogStartEvent()
                .setNpcName(npcName);
    }
}
