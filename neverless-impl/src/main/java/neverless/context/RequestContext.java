package neverless.context;

import neverless.domain.quest.AbstractQuest;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.quest.QuestContainer;
import neverless.repository.PlayerRepository;
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
    private PlayerRepository playerRepository;
    @Autowired
    private QuestContainer questContainer;
    @Autowired
    private SessionUtil sessionUtil;

    private Map<String, Map<String, String>> questCache = new ConcurrentHashMap<>();

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
        String session = sessionUtil.getCurrentSessionId();
        Map<String, String> questStates = questCache.get(session);
        if (questStates == null) {
            questStates = new HashMap<>();
            questCache.put(session, questStates);
        }
        return questStates;
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

    public int getTurnNumber() {
        Player player = playerRepository.get();
        return player.getTurnNumber();
    }
}