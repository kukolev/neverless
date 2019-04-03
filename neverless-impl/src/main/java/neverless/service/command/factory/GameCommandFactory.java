package neverless.service.command.factory;

import neverless.service.command.impl.GameStartNewGameCommand;
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