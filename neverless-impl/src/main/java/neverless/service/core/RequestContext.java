package neverless.service.core;

import lombok.Getter;
import neverless.domain.dialog.quest.AbstractQuest;
import neverless.domain.event.AbstractEvent;
import neverless.game.quest.QuestContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext {

    @Autowired
    private QuestContainer questContainer;
    @Getter
    private Map<String, String> questStates = new HashMap<>();

    public void initQuestStates() {
        questContainer.findAll()
                .forEach(q -> questStates.put(q.getQuestId(), q.getJournalHash()));
    }

    public List<String> findUpdatedQuests() {
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
}
