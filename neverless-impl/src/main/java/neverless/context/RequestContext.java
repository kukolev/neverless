package neverless.context;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.quest.AbstractQuest;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.quest.QuestContainer;
import neverless.repository.PlayerRepository;
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
    private PlayerRepository playerRepository;
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

    public int getTurnNumber() {
        Player player = playerRepository.get();
        return player.getTurnNumber();
    }
}