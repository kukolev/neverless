package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.portal.LocationPortal;
import neverless.service.util.LocalMapService;

@Data
@Accessors(chain = true)
public class PlayerPortalEnterCommand extends AbstractCommand {

    private Player player;
    private EventContext eventContext;
    private LocationPortal portal;
    private LocalMapService localMapService;

    @Override
    public BehaviorState execute() {
        Coordinate enterPoint = portal.getEnterPoint();
        localMapService.makeStep(player, enterPoint.getX(), enterPoint.getY());
        return BehaviorState.MOVE;
    }

    @Override
    public boolean checkFinished() {
        return false;
    }
}
