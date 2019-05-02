package neverless.service.model.command.factory;

import neverless.service.model.command.impl.GameStartNewGameCommand;
import neverless.game.GameLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameCommandFactory {

    @Autowired
    private GameLoader gameLoader;

    public GameStartNewGameCommand createStartNewGameCommand() {
        return new GameStartNewGameCommand()
                .setLoader(gameLoader);
    }
}