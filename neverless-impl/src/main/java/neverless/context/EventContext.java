package neverless.context;

import lombok.Getter;
import neverless.domain.event.AbstractEvent;
import neverless.domain.event.DialogSelectPhraseEvent;
import neverless.domain.event.DialogStartEvent;
import neverless.domain.event.FightingEnemyHitEvent;
import neverless.domain.event.FightingEnemyKilledEvent;
import neverless.domain.event.FightingEnemyMissEvent;
import neverless.domain.event.FightingPlayerHitEvent;
import neverless.domain.event.FightingPlayerMissEvent;
import neverless.domain.event.InventoryLeftHandEquipEvent;
import neverless.domain.event.InventoryRightHandEquipEvent;
import neverless.domain.event.JournalUpdateEvent;
import neverless.domain.event.MapGoEvent;
import neverless.domain.event.MapGoImpossibleEvent;
import neverless.domain.event.PortalEnterEvent;
import neverless.dto.quest.QuestState;
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

    public void addDialogStartEvent(String objectId, String npcName, Integer npcX, Integer npcY) {
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

    public void addFightingEnemyHitEvent(String enemyId, Integer damage) {
        addEvent(new FightingEnemyHitEvent()
                .setEnemyId(enemyId)
                .setDamage(damage));
    }

    public void addFightingEnemyMissEvent(String enemyId) {
        addEvent(new FightingEnemyMissEvent()
                .setEnemyId(enemyId));
    }

    public void addFightingPlayerHitEvent(String enemyId, Integer damage) {
        addEvent(new FightingPlayerHitEvent()
                .setEnemyId(enemyId)
                .setDamage(damage));
    }

    public void addFightingPlayerMissEvent(String enemyId) {
        addEvent(new FightingPlayerMissEvent()
                .setEnemyId(enemyId));
    }

    public void addFightingEnemyKillEvent(String enemyId) {
        addEvent(new FightingEnemyKilledEvent()
        .setEnemyId(enemyId));
    }
}