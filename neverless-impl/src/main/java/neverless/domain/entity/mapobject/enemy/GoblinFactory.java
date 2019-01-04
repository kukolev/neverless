package neverless.domain.entity.mapobject.enemy;

import neverless.domain.entity.item.weapon.Sword;
import neverless.repository.ItemRepository;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;


@Component
@Transactional
public class GoblinFactory extends AbstractEnemyFactory {

    private static final Integer GOBLIN_HP = 20;
    private static final Integer GOBLIN_SPEED = 1;
    private static final String GOBLIN_RUST_SWORD_TITLE = "Rust sword";
    private static final Integer GOBLIN_RUST_SWORD_POWER = 1;

    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private ItemRepository itemRepository;

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
