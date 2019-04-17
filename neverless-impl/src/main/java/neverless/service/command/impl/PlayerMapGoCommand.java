package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.domain.entity.mapobject.Player;
import neverless.service.util.LocalMapService;

@Data
@Accessors(chain = true)
public class PlayerMapGoCommand extends AbstractCommand {

    private int x;
    private int y;
    private Player player;
    private LocalMapService localMapService;
    private EventContext eventContext;

    @Override
    public BehaviorState execute() {
        localMapService.makeStep(player, x, y);
        return BehaviorState.MOVE;
    }

    @Override
    public boolean checkFinished() {
        return x == player.getX() && y == player.getY();
    }
}