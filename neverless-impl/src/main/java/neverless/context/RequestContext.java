package neverless.context;

import neverless.domain.model.quest.AbstractQuest;
import neverless.domain.model.quest.QuestContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RequestContext {

    @Autowired
    private QuestContainer questContainer;

    private Map<String, String> questCache = new ConcurrentHashMap<>();
    private AtomicInteger turnsCounter = new AtomicInteger();

    /**
     * Initializes states for every quest and stores them.
     */
    public void initQuestStates() {
        questContainer.findAll()
                .forEach(q -> questCache.put(q.getQuestId(), q.getJournalHash()));
    }

    /**
     * Returns list of changed quest identifiers.
     * Quest considered as changed if any difference of journal hash in current time and data, stored in cache.
     */
    public List<String> findUpdatedQuests() {
        List<String> ids = new ArrayList<>();
        questCache.forEach((k, v) -> {
            AbstractQuest quest = questContainer.finaById(k);
            String newHash = quest.getJournalHash();
            String oldHash = questCache.get(k);
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
        return turnsCounter.get();
    }

    /**
     * Increments turn number for current game session and returns the number value.
     */
    public int incTurnNumber() {
        return turnsCounter.incrementAndGet();
    }
}