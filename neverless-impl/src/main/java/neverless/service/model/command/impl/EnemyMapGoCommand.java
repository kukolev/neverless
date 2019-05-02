package neverless.service.model.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.model.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemy;
import neverless.service.model.util.LocalMapService;

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
