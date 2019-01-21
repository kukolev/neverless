package neverless.service.core;

import neverless.command.AbstractCommand;
import neverless.command.StartNewGameCommand;
import neverless.command.player.WaitCommand;
import neverless.domain.entity.Game;
import neverless.service.behavior.BehaviorRouterService;
import neverless.service.util.GameService;
import neverless.service.util.NewGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandHandler {

    @Autowired
    private GameService gameService;
    @Autowired
    private BehaviorRouterService behaviorRouterService;
    @Autowired
    private NewGameService newGameService;

    public void processCommand(AbstractCommand command) {

        if (!(command instanceof WaitCommand)) {
            if (command instanceof StartNewGameCommand) {
                newGameService.startNewGame();
            } else {
                Game game = gameService.getGame();
                game.getPlayer().setCommand(command);
            }
        }
        behaviorRouterService.processObjects();
    }
}
