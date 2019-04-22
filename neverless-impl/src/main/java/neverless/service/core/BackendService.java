package neverless.service.core;

import neverless.context.GameContext;
import neverless.domain.entity.mapobject.Player;
import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.service.command.impl.GameStartNewGameCommand;
import neverless.service.core.ai.EnemyCommander;
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
    private GameContext gameContext;
    @Autowired
    private EnemyCommander enemyCommander;

    public void resolveCommand(AbstractCommand command) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        processCommand(command);
        enemyCommander.processEnemies();
        questService.generateQuestEvents();
        requestContext.incTurnNumber();
    }

    /**
     * Performs user's command.
     *
     * @param command   command initiated by user.
     */
    public void processCommand(AbstractCommand command) {
        if (command instanceof GameStartNewGameCommand) {
            command.execute();
            return;
        }

        Player player = gameContext.getPlayer();
        if (command != null && (!command.equals(player.getCommand()))) {
            player.setCommand(command);
        }

        AbstractCommand curCommand = player.getCommand();
        if (curCommand != null) {
            curCommand.execute();
            if (curCommand.checkFinished()) {
                player.setCommand(null);
            }
        }
    }
}