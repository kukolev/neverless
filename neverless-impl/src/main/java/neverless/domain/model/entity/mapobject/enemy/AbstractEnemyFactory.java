package neverless.domain.model.entity.mapobject.enemy;

import org.springframework.stereotype.Component;

@Component
public abstract class AbstractEnemyFactory {

    /**
     * Creates and returns Goblin with weapon.
     */
    public abstract AbstractEnemy create();

 }
