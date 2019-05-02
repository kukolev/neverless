package neverless.domain.model.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.inventory.Inventory;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractLiveObject extends AbstractPhysicalObject {

    private int hitPoints;
    private Inventory inventory = new Inventory();
    private int experience;

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
