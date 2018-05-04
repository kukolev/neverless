package neverless.service;

import neverless.domain.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandRouter {

    @Autowired
    private GameCommandService gameCommandService;
    @Autowired
    private LocalCommandService localCommandService;

    public void route(Command command, Map<String, String> bundle) {
        switch (command.getCommandType()) {
            case GAME_COMMAND: gameCommandService.resolve(command, bundle); break;
            case LOCAL_COMMAND: localCommandService.resolve(command); break;
        }
    }
}
