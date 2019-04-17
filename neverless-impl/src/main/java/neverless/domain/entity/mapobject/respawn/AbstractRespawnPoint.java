package neverless.domain.entity.mapobject.respawn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.PlatformShape;
import neverless.domain.entity.mapobject.AbstractPhysicalObject;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.enemy.EnemyFactory;

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
    public abstract Class<? extends EnemyFactory> getEnemyFactory();

    public PlatformShape getPlatformShape() {
        return PlatformShape.CUSTOM;
    }
}