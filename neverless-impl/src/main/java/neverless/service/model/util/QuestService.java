package neverless.service.model.util;

import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.quest.AbstractQuest;
import neverless.domain.model.quest.QuestContainer;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.service.model.GameRepository;
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
    private GameRepository gameRepository;

    public void generateQuestEvents() {
        Player player = gameRepository.getPlayer();
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