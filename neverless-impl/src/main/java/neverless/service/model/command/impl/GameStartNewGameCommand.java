package neverless.service.model.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.model.command.AbstractCommand;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.game.GameBuilder;

@Data
@Accessors(chain = true)
public class GameStartNewGameCommand extends AbstractCommand {

    private GameBuilder loader;

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
