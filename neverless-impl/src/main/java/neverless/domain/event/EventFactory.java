package neverless.domain.event;

import org.springframework.stereotype.Service;

@Service
public class EventFactory {

    public Event createMapGoDownEvent() {
        return new Event(EventType.EVENT_MAP_GO_DOWN);
    }

    public Event createMapGoUpEvent() {
        return new Event(EventType.EVENT_MAP_GO_UP);
    }

    public Event createMapGoLeftEvent() {
        return new Event(EventType.EVENT_MAP_GO_LEFT);
    }

    public Event createMapGoRightEvent() {
        return new Event(EventType.EVENT_MAP_GO_RIGHT);
    }

    public Event createMapImpassableEvent() {
        return new Event(EventType.EVENT_MAP_IMPASSABLE);
    }

    public final String EVENT_PARAM_PHRASE_NUMBER = "phraseNumber";

    public Event createDialogPhraseSelectedEvent(Integer phraseNumber) {
        Event event = new Event(EventType.EVENT_DIALOG_PHRASE_SELECTED);
        event.addParam(EVENT_PARAM_PHRASE_NUMBER, phraseNumber);
        return event;
    }

    public final String EVENT_PARAM_NPC_NAME = "npcName";

    public Event createDialogStartedEvent(String npcName) {
        Event event = new Event(EventType.EVENT_DIALOG_START);
        event.addParam(EVENT_PARAM_NPC_NAME, npcName);
        return event;
    }
}
