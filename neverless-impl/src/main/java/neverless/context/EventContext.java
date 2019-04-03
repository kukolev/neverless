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
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EventContext {

    @Autowired
    private SessionUtil sessionUtil;

    @Getter
    private Map<String, List<AbstractEvent>> eventsCache = new ConcurrentHashMap<>();

    private void addEvent(String objectId, AbstractEvent event) {
        List<AbstractEvent> events = getEvents(objectId);
        events.add(event);
    }

    public List<AbstractEvent> getEvents(String objectId) {
        String sessionId = sessionUtil.getGameId();
        return eventsCache.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    public void clearEvents() {
        eventsCache.clear();
    }

    public void addMapGoEvent(String objectId, int x, int y) {
        addEvent(objectId, new MapGoEvent()
                .setId(objectId)
                .setX(x)
                .setY(y));
    }

    public void addMapGoImpossibleEvent(String objectId) {
        addEvent(objectId, new MapGoImpossibleEvent().setId(objectId));
    }

    public void addDialogSelectPhraseEvent(String objectId, Integer phraseNumber) {
        addEvent(objectId, new DialogSelectPhraseEvent()
                .setPhraseNumber(phraseNumber));
    }

    public void addDialogStartEvent(String objectId, String npcName, Integer npcX, Integer npcY) {
        addEvent(objectId, new DialogStartEvent()
                .setNpcName(npcName));
    }

    public void addJournalUpdateEvent(String objectId, String questTitle, QuestState state) {
        addEvent(objectId,
                new JournalUpdateEvent()
                .setQuestTitle(questTitle)
                .setState(state));
    }

    public void addInventoryRightHandEquipEvent(String objectId) {
        addEvent(objectId, new InventoryRightHandEquipEvent());
    }

    public void addInventoryLeftHandEquipEvent(String objectId) {
        addEvent(objectId, new InventoryLeftHandEquipEvent());
    }

    public void addPortalEnterEvent(String objectId, String location) {
        addEvent(objectId, new PortalEnterEvent());
    }

    public void addFightingEnemyHitEvent(String enemyId, Integer damage) {
        addEvent(enemyId, new FightingEnemyHitEvent()
                .setEnemyId(enemyId)
                .setDamage(damage));
    }

    public void addFightingEnemyMissEvent(String enemyId) {
        addEvent(enemyId, new FightingEnemyMissEvent()
                .setEnemyId(enemyId));
    }

    public void addFightingPlayerHitEvent(String playerId, String enemyId, Integer damage) {
        addEvent(playerId, new FightingPlayerHitEvent()
                .setEnemyId(enemyId)
                .setDamage(damage));
    }

    public void addFightingPlayerMissEvent(String playerId, String enemyId) {
        addEvent(playerId, new FightingPlayerMissEvent()
                .setEnemyId(enemyId));
    }

    public void addFightingEnemyKillEvent(String playerId, String enemyId) {
        addEvent(playerId, new FightingEnemyKilledEvent()
        .setEnemyId(enemyId));
    }
}