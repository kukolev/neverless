package neverless.model;

import neverless.dto.GameStateDto;
import neverless.model.command.AbstractCommand;
import neverless.model.command.FightingAttackCommand;
import neverless.model.command.MapGoDownCommand;
import neverless.model.command.MapGoDownLeftCommand;
import neverless.model.command.MapGoDownRightCommand;
import neverless.model.command.MapGoLeftCommand;
import neverless.model.command.MapGoRightCommand;
import neverless.model.command.MapGoUpCommand;
import neverless.model.command.MapGoUpLeftCommand;
import neverless.model.command.MapGoUpRightCommand;
import neverless.model.command.StartNewGameCommand;
import neverless.model.command.WaitCommand;
import neverless.model.exception.UnsupportedCommandException;
import neverless.service.core.BackendService;
import neverless.view.RootPane;
import neverless.view.ViewState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ResolverRouterService {

    @Autowired
    private BackendService backendService;
    @Autowired
    private RootPane rootPane;

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
        // Map Go Down Right command
        if (command instanceof MapGoDownRightCommand) {
            return resolve((MapGoDownRightCommand) command);
        }
        // Map Go Down Left command
        if (command instanceof MapGoDownLeftCommand) {
            return resolve((MapGoDownLeftCommand) command);
        }
        // Map Go Up command
        if (command instanceof MapGoUpCommand) {
            return resolve((MapGoUpCommand) command);
        }
        // Map Go Up Right command
        if (command instanceof MapGoUpRightCommand) {
            return resolve((MapGoUpRightCommand) command);
        }
        // Map Go Up Left command
        if (command instanceof MapGoUpLeftCommand) {
            return resolve((MapGoUpLeftCommand) command);
        }
        // Map Go Left command
        if (command instanceof MapGoLeftCommand) {
            return resolve((MapGoLeftCommand) command);
        }
        // Map Go Right command
        if (command instanceof MapGoRightCommand) {
            return resolve((MapGoRightCommand) command);
        }
        // Attack Enemy command
        if (command instanceof FightingAttackCommand) {
            return resolve((FightingAttackCommand) command);
        }

        throw new UnsupportedCommandException();
    }

    private GameStateDto resolve(StartNewGameCommand command) {
        GameStateDto gameStateDto = backendService.cmdStartNewGame();
        rootPane.setViewState(ViewState.LOCAL_MAP);
        return gameStateDto;
    }

    private GameStateDto resolve(WaitCommand command) {
        return backendService.cmdWait();    }

    private GameStateDto resolve(MapGoDownCommand command) {
        return backendService.cmdMoveDown();
    }

    private GameStateDto resolve(MapGoDownRightCommand command) {
        return backendService.cmdMoveDownRight();
    }

    private GameStateDto resolve(MapGoDownLeftCommand command) {
        return backendService.cmdMoveDownLeft();
    }

    private GameStateDto resolve(MapGoUpCommand command) {
        return backendService.cmdMoveUp();
    }

    private GameStateDto resolve(MapGoUpRightCommand command) {
        return backendService.cmdMoveUpRight();
    }

    private GameStateDto resolve(MapGoUpLeftCommand command) {
        return backendService.cmdMoveUpLeft();
    }

    private GameStateDto resolve(MapGoLeftCommand command) {
        return backendService.cmdMoveLeft();
    }

    private GameStateDto resolve(MapGoRightCommand command) {
        return backendService.cmdMoveRight();
    }

    private GameStateDto resolve(FightingAttackCommand command) {
        return backendService.cmdFightingAttack(command.getEnemyId());
    }
}