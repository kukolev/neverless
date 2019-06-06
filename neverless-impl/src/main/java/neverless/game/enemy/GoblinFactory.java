package neverless.game.enemy;

import neverless.domain.model.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemyFactory;
import neverless.domain.model.entity.item.weapon.Sword;
import org.springframework.stereotype.Component;

import static neverless.game.Signatures.IMG_GOBLIN;

@Component
public class GoblinFactory extends AbstractEnemyFactory {

    private static final int GOBLIN_HP = 20;
    private static final int GOBLIN_SPEED = 1;
    private static final String GOBLIN_RUST_SWORD_TITLE = "Rust sword";
    private static final int GOBLIN_RUST_SWORD_POWER = 1;
    private static final int GOBLIN_EXPERIENCE = 10;

    /**
     * Creates and returns Goblin with weapon.
     */
    @Override
    public AbstractEnemy create() {
        Sword sword = createGoblinSword();

        AbstractEnemy goblin = new Goblin();
        goblin
                .setSignature(IMG_GOBLIN)
                .setSpeed(GOBLIN_SPEED)
                .setHitPoints(GOBLIN_HP)
                .setExperience(GOBLIN_EXPERIENCE);
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
