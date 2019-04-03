package neverless.service.command.factory;

import neverless.service.command.impl.EnemyAttackCommand;
import neverless.service.command.impl.EnemyMapGoCommand;
import neverless.service.command.impl.EnemyWaitCommand;
import neverless.context.EventContext;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.service.util.LocalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnemyCommandFactory {

    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private EventContext eventContext;

    public EnemyMapGoCommand createEnemyMapGoCommand(AbstractEnemy enemy, int x, int y) {
        return new EnemyMapGoCommand()
                .setX(x)
                .setY(y)
                .setEnemy(enemy)
                .setEventContext(eventContext)
                .setLocalMapService(localMapService);
    }

    public EnemyWaitCommand createEnemyWaitCommand(int waitTime) {
        return new EnemyWaitCommand()
                .setWaitTime(waitTime);
    }

    public EnemyAttackCommand createEnemyAttackCommand() {
        return new EnemyAttackCommand();
    }
}