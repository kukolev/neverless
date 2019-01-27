package neverless.domain.entity.mapobject.enemy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.Direction;
import neverless.PlatformShape;
import neverless.domain.entity.item.weapon.AbstractMeleeWeapon;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.BehaviorStage;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.MapObjectMetaType;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AbstractEnemy extends AbstractMapObject {

    private Integer hitPoints;
    private AbstractRespawnPoint respawnPoint;
    private List<AbstractMeleeWeapon> weapons = new ArrayList<>();
    private Integer bornX;
    private Integer bornY;
    private Integer areaX;
    private Integer areaY;
    private String signature;
    private Integer agrRange = 50; // todo: should be in constants
    private Direction walkDirection = Direction.NONE;

    /**
     * Count of turns while enemy walks in one direction (walkDirection field)
     */
    private Integer walkTime = 0;
    private BehaviorStage behaviorStage;
    private Integer speed;

    /**
     * Decreases amount of hit points.
     * Hit points could not be less than zero.
     *
     * @param damage    impacted damage.
     */
    public void decreaseHitPoints(int damage) {
        if (hitPoints > damage) {
            hitPoints -= damage;
        } else {
            hitPoints = 0;
        }
    }

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.ENEMY;
    }

    @Override
    public PlatformShape getPlatformShape() {
        return PlatformShape.ELLIPSE;
    }
}