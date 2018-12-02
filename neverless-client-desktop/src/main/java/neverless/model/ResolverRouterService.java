package neverless.model;

import neverless.dto.screendata.player.GameStateDto;
import neverless.model.command.AbstractCommand;
import neverless.model.command.MapGoDownCommand;
import neverless.model.command.MapGoLeftCommand;
import neverless.model.command.MapGoRightCommand;
import neverless.model.command.MapGoUpCommand;
import neverless.model.command.StartNewGameCommand;
import neverless.model.command.WaitCommand;
import neverless.model.exception.UnsupportedCommandException;
import neverless.resource.GameControllerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ResolverRouterService {

    @Autowired
    private GameControllerResource backend;

    /**
     * Resolves command via mapped resolver. Returns GameStateDto.
     *
     * @param command   command that should be resolved.
     */
    public GameStateDto resolve(AbstractCommand command) {
        // Start New Game command
        if (command instanceof StartNewGameCommand) {
            return resolve((StartNewGameCommand) command);
        }
        // Wait command
        if (command instanceof WaitCommand) {
            return resolve((WaitCommand) command);
        }
        // Map Go Down command
        if (command instanceof MapGoDownCommand) {
            return resolve((MapGoDownCommand) command);
        }
        // Map Go Up command
        if (command instanceof MapGoUpCommand) {
            return resolve((MapGoUpCommand) command);
        }
        // Map Go Left command
        if (command instanceof MapGoLeftCommand) {
            return resolve((MapGoLeftCommand) command);
        }
        // Map Go Right command
        if (command instanceof MapGoRightCommand) {
            return resolve((MapGoRightCommand) command);
        }

        throw new UnsupportedCommandException();
    }

    private GameStateDto resolve(StartNewGameCommand command) {
        return backend.cmdStartNewGame();
    }
    private GameStateDto resolve(WaitCommand command) {
        return backend.cmdWait();    }

    private GameStateDto resolve(MapGoDownCommand command) {
        return backend.cmdMoveDown();
    }

    private GameStateDto resolve(MapGoUpCommand command) {
        return backend.cmdMoveUp();
    }

    private GameStateDto resolve(MapGoLeftCommand command) {
        return backend.cmdMoveLeft();
    }

    private GameStateDto resolve(MapGoRightCommand command) {
        return backend.cmdMoveRight();
    }
}