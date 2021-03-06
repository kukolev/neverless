package neverless.core;

import neverless.service.model.BackendService;
import neverless.service.model.command.impl.GameStartNewGameCommand;
import neverless.service.model.command.AbstractCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandResolver {

    @Autowired
    private BackendService backendService;
    @Autowired
    private LocalMapPane localMapPane;

    /**
     * Resolves command via mapped resolver.
     *
     * @param command   command that should be resolved.
     */
    public void resolve(AbstractCommand command) {
        // Start New Game command
        if (command instanceof GameStartNewGameCommand) {
            localMapPane.show();
        }
        backendService.resolveCommand(command);
    }
}