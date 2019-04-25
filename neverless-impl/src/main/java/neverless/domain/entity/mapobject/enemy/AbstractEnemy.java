package neverless.domain.entity.mapobject.enemy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.PlatformShape;
import neverless.domain.entity.mapobject.AbstractLiveObject;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;

import static neverless.Constants.ENEMY_DEFAULT_AGGRESSIVE_RANGE;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AbstractEnemy extends AbstractLiveObject {

    private AbstractRespawnPoint respawnPoint;
    private Integer bornX;
    private Integer bornY;
    private Integer areaX;
    private Integer areaY;
    private String signature;
    private Integer agrRange = ENEMY_DEFAULT_AGGRESSIVE_RANGE;

    /**
     * Count of turns while enemy walks in one direction (walkDirection field)
     */
    private BehaviorState behaviorState;
    private Integer speed;

    @Override
    public PlatformShape getPlatformShape() {
        return PlatformShape.ELLIPSE;
    }
}