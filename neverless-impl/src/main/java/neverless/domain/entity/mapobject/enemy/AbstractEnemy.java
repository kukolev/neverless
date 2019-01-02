package neverless.domain.entity.mapobject.enemy;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Direction;
import neverless.PlatformShape;
import neverless.domain.entity.item.weapon.AbstractMeleeWeapon;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.EnemyBehavior;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.MapObjectMetaType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractEnemy extends AbstractMapObject {

    @Column
    private Integer hitPoints;

    @OneToOne
    private AbstractRespawnPoint respawnPoint;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AbstractMeleeWeapon> weapons = new ArrayList<>();

    @Column
    private Integer bornX;

    @Column
    private Integer bornY;

    @Column
    private Integer areaX;

    @Column
    private Integer areaY;

    @Column
    private Integer agrRange = 7; // todo: should be in constants

    @Column
    private Direction walkDirection;

    @Column
    private EnemyBehavior behavior;

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