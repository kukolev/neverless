package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.domain.entity.BehaviorState;
import neverless.game.GameLoader;

@Data
@Accessors(chain = true)
public class GameStartNewGameCommand extends AbstractCommand {

    private GameLoader loader;

    @Override
    public BehaviorState execute() {
        loader.createNewGame();
        return BehaviorState.IDLE;
    }

    @Override
    public boolean checkFinished() {
        return true;
    }
}
