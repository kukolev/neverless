package neverless.service.model.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.model.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.model.entity.mapobject.Player;
import neverless.service.model.util.LocalMapService;

@Data
@Accessors(chain = true)
public class PlayerMapGoCommand extends AbstractCommand {

    private int x;
    private int y;
    private Player player;
    private LocalMapService localMapService;
    private EventContext eventContext;

    @Override
    public BehaviorState onExecute() {
        localMapService.makeStep(player, x, y);
        return BehaviorState.MOVE;
    }

    @Override
    public boolean checkFinished() {
        return x == player.getX() && y == player.getY();
    }
}