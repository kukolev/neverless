package neverless.domain.model.entity.mapobject.enemy;

import neverless.service.model.command.factory.EnemyCommandFactory;
import neverless.domain.model.entity.item.weapon.Sword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static neverless.util.Constants.ENEMY_DEFAULT_WAIT_TIME;
import static neverless.game.Signatures.IMG_GOBLIN;

@Component
public class EnemyFactory {

    private static final int GOBLIN_HP = 20;
    private static final int GOBLIN_SPEED = 1;
    private static final String GOBLIN_RUST_SWORD_TITLE = "Rust sword";
    private static final int GOBLIN_RUST_SWORD_POWER = 1;
    private static final int GOBLIN_EXPERIENCE = 10;

    @Autowired
    private EnemyCommandFactory commandFactory;

    /**
     * Creates and returns Goblin with weapon.
     */
    public AbstractEnemy create() {
        Sword sword = createGoblinSword();

        AbstractEnemy goblin = new AbstractEnemy();
        goblin
                .setSignature(IMG_GOBLIN)
                .setSpeed(GOBLIN_SPEED)
                .setHitPoints(GOBLIN_HP)
                .setExperience(GOBLIN_EXPERIENCE)
                .setCommand(commandFactory.createEnemyWaitCommand(ENEMY_DEFAULT_WAIT_TIME));
        goblin
                .getInventory()
                .getEquipment()
                .setWeapon(sword);
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