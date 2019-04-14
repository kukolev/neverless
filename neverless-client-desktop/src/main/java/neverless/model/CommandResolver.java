package neverless.model;

import neverless.service.command.impl.GameStartNewGameCommand;
import neverless.service.command.AbstractCommand;
import neverless.service.core.BackendService;
import neverless.window.RootPane;
import neverless.window.ViewState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CommandResolver {

    @Autowired
    private BackendService backendService;
    @Autowired
    private RootPane rootPane;

    /**
     * Resolves command via mapped resolver.
     *
     * @param command   command that should be resolved.
     */
    public void resolve(AbstractCommand command) {
        // Start New Game command
        if (command instanceof GameStartNewGameCommand) {
            rootPane.setViewState(ViewState.LOCAL_MAP);
        }
        backendService.resolveCommand(command);
    }
}