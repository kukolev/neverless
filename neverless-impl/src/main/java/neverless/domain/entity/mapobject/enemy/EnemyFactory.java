package neverless.domain.entity.mapobject.enemy;

import neverless.service.command.factory.EnemyCommandFactory;
import neverless.domain.entity.item.weapon.Sword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static neverless.Constants.ENEMY_DEFAULT_WAIT_TIME;
import static neverless.Resources.IMG_GOBLIN;

@Component
public class EnemyFactory {

    private static final Integer GOBLIN_HP = 20;
    private static final Integer GOBLIN_SPEED = 1;
    private static final String GOBLIN_RUST_SWORD_TITLE = "Rust sword";
    private static final Integer GOBLIN_RUST_SWORD_POWER = 1;

    @Autowired
    private EnemyCommandFactory commandFactory;

    /**
     * Creates and returns Goblin with weapon.
     */
    public AbstractEnemy create() {
        Sword sword = createGoblinSword();

        AbstractEnemy goblin = new AbstractEnemy();
        goblin
                .setHitPoints(GOBLIN_HP)
                .setSpeed(GOBLIN_SPEED)
                .setSignature(IMG_GOBLIN)
                .setCommand(commandFactory.createEnemyWaitCommand(ENEMY_DEFAULT_WAIT_TIME));
        goblin
                .getWeapons().add(sword);
        return goblin;
    }

    /**
     * Creates and returns sword for goblin.
     */
    private Sword createGoblinSword() {
        Sword sword = new Sword();
        sword
                .setPower(GOBLIN_RUST_SWORD_POWER)
                .setTitle(GOBLIN_RUST_SWORD_TITLE);
        return sword;
    }
}
