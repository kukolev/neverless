package neverless.service.core;

import neverless.command.Command;
import neverless.service.util.NewGameService;
import neverless.service.util.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandRouter {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private BehaviorProcessor behaviorProcessor;
    @Autowired
    private NewGameService newGameService;

    public void processCommand(Command command) {

        switch (command.getCommandType()) {
            case GAME_START_NEW_GAME:
                newGameService.startNewGame();
                break;

            default: {
                playerService.setCommand(command);
                behaviorProcessor.processObjects();
            }
        }
    }
}