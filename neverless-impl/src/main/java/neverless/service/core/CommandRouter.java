package neverless.service.core;

import neverless.command.Command;
import neverless.command.CommandType;
import neverless.domain.entity.mapobject.Player;
import neverless.service.util.GameService;
import neverless.service.util.NewGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandRouter {

    @Autowired
    private GameService gameService;
    @Autowired
    private BehaviorRouter behaviorRouterService;
    @Autowired
    private NewGameService newGameService;

    public void processCommand(Command command) {

        switch (command.getCommandType()) {
            case GAME_START_NEW_GAME:
                newGameService.startNewGame();
                break;

            default: {
                if (command.getCommandType() != CommandType.PLAYER_CONTINUE) {
                    Player player = gameService.getGame().getPlayer();
                    player.setCommand(command);
                }
                behaviorRouterService.processObjects();
            }
        }
    }
}