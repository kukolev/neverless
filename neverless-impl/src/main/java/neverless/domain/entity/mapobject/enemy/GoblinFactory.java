package neverless.domain.entity.mapobject.enemy;

import neverless.domain.entity.item.weapon.Sword;
import org.springframework.stereotype.Component;

@Component
public class GoblinFactory extends AbstractEnemyFactory {

    private static final Integer GOBLIN_HP = 20;
    private static final Integer GOBLIN_SPEED = 1;
    private static final String GOBLIN_RUST_SWORD_TITLE = "Rust sword";
    private static final Integer GOBLIN_RUST_SWORD_POWER = 1;

    /**
     * Creates and returns Goblin with weapon.
     */
    @Override
    public AbstractEnemy create() {
        Sword sword = createGoblinSword();

        Goblin goblin = new Goblin();
        goblin
                .setHitPoints(GOBLIN_HP)
                .setSpeed(GOBLIN_SPEED);
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
