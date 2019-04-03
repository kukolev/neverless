package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.domain.entity.BehaviorState;
import neverless.domain.entity.mapobject.Player;

@Data
@Accessors(chain = true)
public class PlayerContinueCommand extends AbstractCommand {

    private Player player;

    @Override
    public BehaviorState execute() {
        return player.getBehavior().getState();
    }

    @Override
    public boolean checkFinished() {
        return false;
    }
}
