package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.util.Coordinate;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.service.util.LocalMapService;

import static neverless.util.CoordinateUtils.calcDirection;
import static neverless.util.CoordinateUtils.calcNextStep;

@Data
@Accessors(chain = true)
public class EnemyMapGoCommand extends AbstractCommand {

    private AbstractEnemy enemy;
    private int x;
    private int y;
    private LocalMapService localMapService;
    private EventContext eventContext;

    @Override
    public BehaviorState onExecute() {
        localMapService.makeStep(enemy, x, y);
        return BehaviorState.MOVE;
    }

    @Override
    public boolean checkFinished() {
        return x == enemy.getX() && y == enemy.getY();
    }
}
