package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.inventory.Inventory;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractLiveObject extends AbstractPhysicalObject {

    private int hitPoints;
    private Inventory inventory = new Inventory();

    /**
     * Decreases amount of hit points.
     * Hit points could not be less than zero.
     *
     * @param damage    impacted damage.
     */
    public void decreaseHitPoints(int damage) {
        hitPoints -= damage;
    }
}
