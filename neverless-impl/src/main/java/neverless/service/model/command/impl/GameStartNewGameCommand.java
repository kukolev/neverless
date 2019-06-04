package neverless.service.model.command.impl;

import neverless.domain.model.entity.Game;
import neverless.service.model.GameRepository;
import neverless.service.model.command.AbstractCommand;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.game.GameBuilder;

public class GameStartNewGameCommand extends AbstractCommand {

    private GameBuilder builder;
    private GameRepository cache;

    public GameStartNewGameCommand(GameBuilder builder, GameRepository cache) {
        this.builder = builder;
        this.cache = cache;
    }

    @Override
    public BehaviorState onExecute() {
        Game game = builder.createNewGame();
        cache.save(game);
        return BehaviorState.IDLE;
    }

    @Override
    public boolean checkFinished() {
        return true;
    }
}
