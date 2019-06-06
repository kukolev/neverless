package neverless.domain.model.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.inventory.Inventory;
import neverless.domain.model.entity.mapobject.loot.AbstractLootFactory;
import neverless.domain.model.entity.mapobject.loot.LootContainer;

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

    /**
     * Generates and returns loot container.
     */
    public LootContainer getLoot() {
        return getLootFactory().createLoot();
    }

    /**
     * Returns factory for loot creation.
     * Method should be overwritten in implementations.
     */
    protected abstract AbstractLootFactory getLootFactory();

}
