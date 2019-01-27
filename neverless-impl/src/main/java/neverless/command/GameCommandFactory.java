package neverless.command;

import neverless.command.game.StartNewGamePayload;
import org.springframework.stereotype.Component;

@Component
public class GameCommandFactory {

    public Command createStartNewGameCommand() {
        return new Command()
                .setCommandType(CommandType.GAME_START_NEW_GAME)
                .setPayload(new StartNewGamePayload());
    }
}