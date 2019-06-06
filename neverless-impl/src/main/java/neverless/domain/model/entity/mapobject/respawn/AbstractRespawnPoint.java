package neverless.domain.model.entity.mapobject.respawn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.PlatformShape;
import neverless.domain.model.entity.mapobject.AbstractPhysicalObject;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemyFactory;
import neverless.domain.model.entity.mapobject.loot.AbstractLootFactory;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractRespawnPoint extends AbstractPhysicalObject {

    private Integer areaX = 100;
    private Integer areaY = 100;

    /** Turn number when the enemy is alive */
    private Integer lastTurnInLife = 0;

    /** Period, after that enemy should be respawned (turns) */
    private Integer respawnPeriod = 100;

    /** Enemy, created by this point */
    private AbstractEnemy enemy;

    /** Returns a factory which able to create enemies for this point. */
    public abstract AbstractEnemyFactory getEnemyFactory();

    public abstract AbstractLootFactory getLootFactory();

    public PlatformShape getPlatformShape() {
        return PlatformShape.CUSTOM;
    }
}