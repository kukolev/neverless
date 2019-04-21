package neverless.service.command.factory;

import neverless.context.GameContext;
import neverless.domain.entity.mapobject.Player;
import neverless.service.command.impl.EnemyAttackCommand;
import neverless.service.command.impl.EnemyMapGoCommand;
import neverless.service.command.impl.EnemyWaitCommand;
import neverless.context.EventContext;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.service.util.CombatService;
import neverless.service.util.LocalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnemyCommandFactory {

    @Autowired
    private GameContext gameContext;
    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private CombatService combatService;

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

    public EnemyAttackCommand createEnemyAttackCommand(AbstractEnemy enemy) {
        Player player = gameContext.getPlayer();
        return new EnemyAttackCommand(enemy, player, localMapService, eventContext, combatService);
    }
}