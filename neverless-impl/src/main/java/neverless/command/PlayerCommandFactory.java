package neverless.command;

import neverless.command.player.PlayerAttackPayload;
import neverless.command.player.PlayerContinuePayload;
import neverless.command.player.PlayerMapGoPayload;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandFactory {

    public Command createPlayerAttackCommand(AbstractEnemy enemy) {
        return new Command()
                .setCommandType(CommandType.PLAYER_ATTACK)
                .setPayload(new PlayerAttackPayload()
                        .setEnemy(enemy));
    }

    public Command createPlayerMapGoCommand(int x, int y) {
        return new Command()
                .setCommandType(CommandType.PLAYER_MOVE)
                .setPayload(new PlayerMapGoPayload()
                        .setX(x)
                        .setY(y));
    }

    public Command createPlayerContinueCommand() {
        return new Command()
                .setCommandType(CommandType.PLAYER_DO_NOTHING)
                .setPayload(new PlayerContinuePayload());
    }
}
