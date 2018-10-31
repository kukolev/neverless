package neverless.service.core;

import lombok.Getter;
import neverless.domain.event.*;
import neverless.dto.command.Direction;
import neverless.dto.screendata.quest.QuestState;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EventContext {

    @Getter
    private List<AbstractEvent> events = new ArrayList<>();

    public void addMapGoEvent(Direction direction) {
        events.add(new MapGoEvent()
                .setDirection(direction));
    }

    public void addMapGoImpossibleEvent() {
        events.add(new MapGoImpossibleEvent());
    }

    public void addDialogSelectPhraseEvent(Integer phraseNumber) {
        events.add(new DialogSelectPhraseEvent()
                .setPhraseNumber(phraseNumber));
    }

    public void addDialogStartEvent(String npcName, Integer npcX, Integer npcY) {
        events.add(new DialogStartEvent()
                .setNpcName(npcName));
    }

    public void addJournalUpdateEvent(String questTitle, QuestState state) {
        events.add(new JournalUpdateEvent()
                .setQuestTitle(questTitle)
                .setState(state));
    }

    public void addInventoryRightHandEquipEvent() {
        events.add(new InventoryRightHandEquipEvent());
    }

    public void addInventoryLeftHandEquipEvent() {
        events.add(new InventoryLeftHandEquipEvent());
    }

    public void addPortalEnterEvent(String location) {
        events.add(new PortalEnterEvent());
    }
}
