package neverless.context;

import lombok.Getter;
import neverless.domain.model.event.AbstractEvent;
import neverless.domain.model.event.DialogSelectPhraseEvent;
import neverless.domain.model.event.DialogStartEvent;
import neverless.domain.model.event.CombatKillEvent;
import neverless.domain.model.event.CombatHitEvent;
import neverless.domain.model.event.CombatMissEvent;
import neverless.domain.model.event.InventoryLeftHandEquipEvent;
import neverless.domain.model.event.InventoryRightHandEquipEvent;
import neverless.domain.model.event.JournalUpdateEvent;
import neverless.domain.model.event.MapGoEvent;
import neverless.domain.model.event.MapGoImpossibleEvent;
import neverless.domain.model.event.PortalEnterEvent;
import neverless.domain.model.quest.QuestState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventContext {

    @Getter
    private List<AbstractEvent> events = new ArrayList<>();

    private void addEvent(AbstractEvent event) {
        events.add(event);
    }

    public void clearEvents() {
        events.clear();
    }

    public void addMapGoEvent(String objectId, int x, int y, int targetX, int targetY) {
        addEvent(new MapGoEvent()
                .setId(objectId)
                .setX(x)
                .setY(y)
                .setTargetX(targetX)
                .setTargetY(targetY));
    }

    public void addMapGoImpossibleEvent(String objectId) {
        addEvent(new MapGoImpossibleEvent().setId(objectId));
    }

    public void addDialogSelectPhraseEvent(String objectId, Integer phraseNumber) {
        addEvent(new DialogSelectPhraseEvent()
                .setPhraseNumber(phraseNumber));
    }

    public void addDialogStartEvent(String objectId, String npcName) {
        addEvent(new DialogStartEvent()
                .setNpcName(npcName));
    }

    public void addJournalUpdateEvent(String objectId, String questTitle, QuestState state) {
        addEvent(new JournalUpdateEvent()
                .setQuestTitle(questTitle)
                .setState(state));
    }

    public void addInventoryRightHandEquipEvent() {
        addEvent(new InventoryRightHandEquipEvent());
    }

    public void addInventoryLeftHandEquipEvent() {
        addEvent(new InventoryLeftHandEquipEvent());
    }

    public void addPortalEnterEvent(String objectId, String location) {
        addEvent(new PortalEnterEvent());
    }

    public void addCombatHitEvent(String defender, Integer damage) {
        addEvent(new CombatHitEvent()
                .setDefenderId(defender)
                .setDamage(damage));
    }

    public void addCombatMissEvent(String defender) {
        addEvent(new CombatMissEvent()
                .setDefenderId(defender));
    }

    public void addCombatKillEvent(String defender) {
        addEvent(new CombatKillEvent()
        .setDefenderId(defender));
    }
}