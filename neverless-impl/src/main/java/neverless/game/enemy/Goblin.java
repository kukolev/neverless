package neverless.game.enemy;

import neverless.domain.model.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.model.entity.mapobject.loot.AbstractLootFactory;

public class Goblin extends AbstractEnemy {

    @Override
    protected AbstractLootFactory getLootFactory() {
        return getRespawnPoint().getLootFactory();
    }
}
