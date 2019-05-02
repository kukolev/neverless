package neverless.service.model.command.impl;

import neverless.domain.Coordinate;
import neverless.service.model.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.portal.LocationPortal;
import neverless.service.model.util.LocalMapService;
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
