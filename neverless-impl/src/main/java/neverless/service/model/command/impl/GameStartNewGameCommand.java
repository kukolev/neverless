package neverless.service.model.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.model.command.AbstractCommand;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.game.GameLoader;

@Data
@Accessors(chain = true)
public class GameStartNewGameCommand extends AbstractCommand {

    private GameLoader loader;

    @Override
    public BehaviorState onExecute() {
        loader.createNewGame();
        return BehaviorState.IDLE;
    }

    @Override
    public boolean checkFinished() {
        return true;
    }
}
