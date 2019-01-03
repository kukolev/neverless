package neverless.domain.entity.mapobject.respawn;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.PlatformShape;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.enemy.AbstractEnemyFactory;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.MapObjectMetaType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractRespawnPoint extends AbstractMapObject {

    @Column
    private Integer areaX = 3;

    @Column
    private Integer areaY = 3;

    /** Turn number when the enemy is alive */
    @Column
    private Integer lastTurnInLife = 0;

    /** Period, after that enemy should be respawned (turns) */
    @Column
    private Integer respawnPeriod = 3;

    /** Enemy, created by this point */
    @OneToOne
    private AbstractEnemy enemy;

    /** Returns a factory which able to create enemies for this point. */
    public abstract Class<? extends AbstractEnemyFactory> getEnemyFactory();

    /** {@inheritDoc} */
    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.TERRAIN;
    }

    public PlatformShape getPlatformShape() {
        return PlatformShape.CUSTOM;
    }
}