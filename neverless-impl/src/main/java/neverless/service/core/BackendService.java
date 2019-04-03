package neverless.service.core;

import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.dto.GameStateDto;
import neverless.context.GameContext;
import neverless.service.util.DialogService;
import neverless.service.util.EventService;
import neverless.service.util.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackendService {

    @Autowired
    private DialogService dialogService;
    @Autowired
    private QuestService questService;
    @Autowired
    private EventService eventService;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private GameContext gameContext;
    @Autowired
    private PlayerCommander playerCommander;
    @Autowired
    private EnemyCommander enemyCommander;

    // todo: DRY
    private GameStateDto getState() {
        return new GameStateDto()
                .setGame(gameContext.getGame())
                .setDialogScreenDataDto(dialogService.getScreenData())
                .setQuestScreenDataDto(questService.getScreenData())
                .setEventsScreenDataDto(eventService.getEventScreenData())
                .setTurnNumber(requestContext.incTurnNumber());
    }

    public GameStateDto resolveCommand(AbstractCommand command) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerCommander.processCommand(command);
        enemyCommander.processEnemies();
        questService.generateQuestEvents();
        return getState();
    }
}