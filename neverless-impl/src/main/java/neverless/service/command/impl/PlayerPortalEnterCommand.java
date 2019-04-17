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

import static neverless.util.CoordinateUtils.calcDirection;
import static neverless.util.CoordinateUtils.calcNextStep;

@Data
@Accessors(chain = true)
public class PlayerPortalEnterCommand extends AbstractCommand {

    private Player player;
    private EventContext eventContext;
    private LocationPortal portal;
    private LocalMapService localMapService;

    @Override
    public BehaviorState execute() {
        if (portal.getCoordinates().size() > 0) {
            // todo: DRY!
            Coordinate enterPoint = portal.getEnterPoint();
            Coordinate coordinate = calcNextStep(player.getX(), player.getY(), enterPoint.getX(), enterPoint.getY());
            if (localMapService.isPassable(player, coordinate.getX(), coordinate.getY())) {
                player.setX(coordinate.getX());
                player.setY(coordinate.getY());
                player.setDirection(calcDirection(player.getX(), player.getY(), enterPoint.getX(), enterPoint.getY()));
                eventContext.addMapGoEvent(player.getUniqueName(), player.getX(), player.getY(), enterPoint.getX(), enterPoint.getY());
            } else {
                eventContext.addMapGoImpossibleEvent(player.getUniqueName());
            }
            return BehaviorState.MOVE;
        } else {
            throw new IllegalArgumentException("Empty list of coordinates for portal " + portal.getUniqueName());
        }
    }

    @Override
    public boolean checkFinished() {
        return false;
    }
}
