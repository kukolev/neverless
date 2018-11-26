package neverless.model.resolver;

import neverless.dto.screendata.player.ResponseDto;
import neverless.model.command.StartNewGameCommand;
import neverless.resource.GameControllerResourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartNewGameResolver extends AbstractCommandResolver<StartNewGameCommand> {

    @Autowired
    private GameControllerResourceImpl resource;

    @Override
    public ResponseDto resolve(StartNewGameCommand command) {
        return resource.cmdStartNewGame();
    }
}
