package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.domain.entity.mapobject.Player;
import neverless.service.util.LocalMapService;

import static neverless.util.CoordinateUtils.calcNextStep;

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
        Coordinate coordinate = calcNextStep(player.getX(), player.getY(), x, y);
        if (localMapService.isPassable(player, coordinate.getX(), coordinate.getY())) {
            player.setX(coordinate.getX());
            player.setY(coordinate.getY());
            eventContext.addMapGoEvent(player.getUniqueName(), player.getX(), player.getY());
        } else {
            eventContext.addMapGoImpossibleEvent(player.getUniqueName());
        }
        return BehaviorState.MOVE;
    }

    @Override
    public boolean checkFinished() {
        return x == player.getX() && y == player.getY();
    }
}