package neverless.service.core;

import neverless.service.command.AbstractCommand;
import neverless.service.command.impl.GameStartNewGameCommand;
import neverless.service.command.impl.PlayerContinueCommand;
import neverless.domain.entity.BehaviorState;
import neverless.domain.entity.mapobject.Player;
import neverless.context.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommander {

    @Autowired
    private GameContext gameContext;

    public void processCommand(AbstractCommand command) {
        if (command instanceof GameStartNewGameCommand) {
            command.execute();
            return;
        }

        Player player = gameContext.getPlayer();
        if (!(command instanceof PlayerContinueCommand)) {
            player.setCommand(command);
        }

        AbstractCommand curCommand = player.getCommand();
        if (curCommand != null) {
            BehaviorState state = curCommand.execute();
            player.getBehavior().changeState(state);
            player.getBehavior().tick();
            if (curCommand.checkFinished()) {
                player.setCommand(null);
                player.getBehavior().changeState(BehaviorState.IDLE);
            }
        }
    }
}