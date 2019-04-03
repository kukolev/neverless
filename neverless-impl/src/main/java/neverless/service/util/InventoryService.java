package neverless.service.util;

import neverless.domain.entity.inventory.Inventory;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.entity.mapobject.Player;
import neverless.context.EventContext;
import neverless.context.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private GameContext gameContext;
    @Autowired
    private EventContext eventContext;


    /**
     * Equips weapon in right hand.
     * Item added to right hand. Current weapon in right hand goes to bag.
     *
     * @param itemNo order number of item in Bag.
     */
    public void equipRightHand(Integer itemNo) {
        clearRightHand();
        Inventory inventory = loadInventory();
        AbstractHandEquipment newWeapon = inventory.getBag().getWeaponByNumber(itemNo);
        inventory.getEquipment().setRightHand(newWeapon);
        inventory.getBag().remove(newWeapon);
        Player player = gameContext.getPlayer();
        eventContext.addInventoryRightHandEquipEvent(player.getUniqueName());
    }

    /**
     * Equips weapon in left hand.
     * Item added to left hand. Current weapon in left hand goes to bag.
     *
     * @param itemNo order number of item in Bag.
     */
    public void equipLeftHand(Integer itemNo) {
        clearLeftHand();
        Inventory inventory = loadInventory();
        AbstractHandEquipment newWeapon = inventory.getBag().getWeaponByNumber(itemNo);
        inventory.getEquipment().setLeftHand(newWeapon);
        inventory.getBag().remove(newWeapon);
        Player player = gameContext.getPlayer();
        eventContext.addInventoryLeftHandEquipEvent(player.getUniqueName());
    }

    /**
     * Clears right hand. Current weapon in right hand goes to bag.
     */
    public void clearRightHand() {
        Inventory inventory = loadInventory();
        AbstractHandEquipment curWeapon = inventory.getEquipment().getRightHand();
        if (curWeapon != null) {
            inventory.getBag().addLast(curWeapon);
        }
        inventory.getEquipment().setRightHand(null);
    }

    /**
     * Clears left hand. Current weapon in left hand goes to bag.
     */
    public void clearLeftHand() {
        Inventory inventory = loadInventory();
        AbstractHandEquipment curWeapon = inventory.getEquipment().getLeftHand();
        if (curWeapon != null) {
            inventory.getBag().addLast(curWeapon);
        }
        inventory.getEquipment().setLeftHand(null);
    }

    /**
     * Loads player and returns his inventory.
     *
     * @return inventory.
     */
    private Inventory loadInventory() {
        Player player = gameContext.getPlayer();
        return player.getInventory();
    }
}