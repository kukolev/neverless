package neverless.domain.model.entity.mapobject.enemy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.PlatformShape;
import neverless.domain.model.entity.mapobject.AbstractLiveObject;
import neverless.domain.model.entity.mapobject.respawn.AbstractRespawnPoint;

import static neverless.util.Constants.ENEMY_DEFAULT_AGGRESSIVE_RANGE;

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