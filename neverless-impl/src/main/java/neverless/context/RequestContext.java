package neverless.context;

import neverless.domain.quest.AbstractQuest;
import neverless.domain.quest.QuestContainer;
import neverless.service.behavior.PlayerBehaviorService;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestContext {

    @Autowired
    private PlayerBehaviorService playerService;
    @Autowired
    private QuestContainer questContainer;
    @Autowired
    private SessionUtil sessionUtil;

    private Map<String, Map<String, String>> questCache = new ConcurrentHashMap<>();
    private Map<String, Integer> turnsCache = new ConcurrentHashMap<>();

    /**
     * Initializes states for every quest and stores them.
     */
    public void initQuestStates() {
        Map<String, String> questStates = getQuestStates();
        questContainer.findAll()
                .forEach(q -> questStates.put(q.getQuestId(), q.getJournalHash()));
    }

    /**
     * Returns map with quest states for concrete session.
     */
    private Map<String, String> getQuestStates() {
        String session = sessionUtil.getGameId();
        return questCache.computeIfAbsent(session, k -> new HashMap<>());
    }

    /**
     * Returns list of changed quest identifiers.
     * Quest considered as changed if any difference of journal hash in current time and data, stored in cache.
     */
    public List<String> findUpdatedQuests() {
        Map<String, String> questStates = getQuestStates();
        List<String> ids = new ArrayList<>();
        questStates.forEach((k, v) -> {
            AbstractQuest quest = questContainer.finaById(k);
            String newHash = quest.getJournalHash();
            String oldHash = questStates.get(k);
            if (!newHash.equals(oldHash)) {
                ids.add(k);
            }
        });
        return ids;
    }

    /**
     * Returns turn number for current game session.
     */
    public int getTurnNumber() {
        String gameId = sessionUtil.getGameId();
        return turnsCache.computeIfAbsent(gameId, turnNumber -> 0);
    }

    /**
     * Increments turn number for current game session and returns the number value.
     */
    public int incTurnNumber() {
        String gameId = sessionUtil.getGameId();
        int turnNumber = getTurnNumber();
        turnsCache.put(gameId, ++turnNumber);
        return turnNumber;
    }
}