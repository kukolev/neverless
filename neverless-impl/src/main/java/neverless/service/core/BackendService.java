package neverless.service.core;

import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.service.util.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackendService {

    @Autowired
    private QuestService questService;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private PlayerCommander playerCommander;
    @Autowired
    private EnemyCommander enemyCommander;

    public void resolveCommand(AbstractCommand command) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerCommander.processCommand(command);
        enemyCommander.processEnemies();
        questService.generateQuestEvents();
        requestContext.incTurnNumber();
    }
}