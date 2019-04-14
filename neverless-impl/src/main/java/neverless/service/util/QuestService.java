package neverless.service.util;

import neverless.domain.entity.mapobject.Player;
import neverless.domain.quest.AbstractQuest;
import neverless.domain.quest.QuestContainer;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.context.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestService {

    @Autowired
    private QuestContainer repository;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private GameContext gameContext;

    public void generateQuestEvents() {
        Player player = gameContext.getPlayer();
        requestContext.findUpdatedQuests()
                .forEach(id -> {
                    AbstractQuest quest = repository.finaById(id);
                    eventContext.addJournalUpdateEvent(
                            player.getUniqueName(),
                            quest.getTitle(),
                            quest.getState());
                });
    }
}