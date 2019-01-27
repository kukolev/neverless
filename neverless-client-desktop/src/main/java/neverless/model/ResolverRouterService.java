package neverless.model;

import neverless.command.CommandType;
import neverless.dto.GameStateDto;
import neverless.command.Command;
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
    public GameStateDto resolve(Command command) {
        // Start New Game command
        if (command.getCommandType() == CommandType.GAME_START_NEW_GAME) {
            rootPane.setViewState(ViewState.LOCAL_MAP);
        }
        return backendService.resolveCommand(command);
    }
}