package neverless.command;

import neverless.command.enemy.EnemyAttackPayload;
import neverless.command.enemy.EnemyMapGoPayload;
import neverless.command.enemy.EnemyWaitPayload;
import org.springframework.stereotype.Component;

@Component
public class EnemyCommandFactory {

    public Command createEnemyAttackCommand() {
        return new Command()
                .setCommandType(CommandType.ENEMY_ATTACK)
                .setPayload(new EnemyAttackPayload());
    }

    public Command createEnemyMapGoCommand(int x, int y) {
        return new Command()
                .setCommandType(CommandType.ENEMY_WALK)
                .setPayload(new EnemyMapGoPayload()
                        .setX(x)
                        .setY(y));
    }

    public Command createEnemyWaitCommand(int waitTime) {
        return new Command()
                .setCommandType(CommandType.ENEMY_WAIT)
                .setPayload(new EnemyWaitPayload()
                .setWaitTime(waitTime));
    }
}