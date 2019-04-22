package neverless.service.command.impl;

import neverless.util.Coordinate;
import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.portal.LocationPortal;
import neverless.service.util.LocalMapService;
import neverless.util.CoordinateUtils;

public class PlayerPortalEnterCommand extends AbstractCommand {

    private Player player;
    private EventContext eventContext;
    private LocationPortal portal;
    private LocalMapService localMapService;

    private boolean isFinished;

    public PlayerPortalEnterCommand(Player player, EventContext eventContext, LocationPortal portal, LocalMapService localMapService) {
        this.player = player;
        this.eventContext = eventContext;
        this.portal = portal;
        this.localMapService = localMapService;
    }

    @Override
    public BehaviorState onExecute() {
        Coordinate enterPoint = portal.getEnterPoint();
        localMapService.makeStep(player, enterPoint.getX(), enterPoint.getY());
        if (CoordinateUtils.isCoordinatesInRange(player.getX(), player.getY(), enterPoint.getX(), enterPoint.getY(), 100)) {
            localMapService.enterPortal(player, portal);
            isFinished = true;
        }
        return BehaviorState.MOVE;
    }

    @Override
    public boolean checkFinished() {
        return isFinished;
    }
}
