package neverless.context;

import lombok.Getter;
import neverless.domain.event.AbstractEvent;
import neverless.domain.event.DialogSelectPhraseEvent;
import neverless.domain.event.DialogStartEvent;
import neverless.domain.event.EnemyMoveEvent;
import neverless.domain.event.FightingEnemyHitEvent;
import neverless.domain.event.FightingEnemyKillEvent;
import neverless.domain.event.FightingEnemyMissEvent;
import neverless.domain.event.FightingPlayerHitEvent;
import neverless.domain.event.FightingPlayerMissEvent;
import neverless.domain.event.InventoryLeftHandEquipEvent;
import neverless.domain.event.InventoryRightHandEquipEvent;
import neverless.domain.event.JournalUpdateEvent;
import neverless.domain.event.MapGoEvent;
import neverless.domain.event.MapGoImpossibleEvent;
import neverless.domain.event.PortalEnterEvent;
import neverless.dto.command.Direction;
import neverless.dto.screendata.quest.QuestState;
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

    private void addEvent(AbstractEvent event) {
        List<AbstractEvent> events = getEvents();
        events.add(event);
    }

    public List<AbstractEvent> getEvents() {
        String sessionId = sessionUtil.getGameId();
        return eventsCache.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    public void addMapGoEvent(Direction direction) {
        addEvent(new MapGoEvent()
                .setDirection(direction));
    }

    public void addMapGoImpossibleEvent() {
        addEvent(new MapGoImpossibleEvent());
    }

    public void addDialogSelectPhraseEvent(Integer phraseNumber) {
        addEvent(new DialogSelectPhraseEvent()
                .setPhraseNumber(phraseNumber));
    }

    public void addDialogStartEvent(String npcName, Integer npcX, Integer npcY) {
        addEvent(new DialogStartEvent()
                .setNpcName(npcName));
    }

    public void addJournalUpdateEvent(String questTitle, QuestState state) {
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

    public void addPortalEnterEvent(String location) {
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
        addEvent(new FightingEnemyKillEvent()
        .setEnemyId(enemyId));
    }

    public void addEnemyMoveEvent(String enemyId) {
        addEvent(new EnemyMoveEvent()
                .setEnemyId(enemyId));
    }
}