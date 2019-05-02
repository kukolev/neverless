package neverless.service.model.util;

import neverless.domain.model.entity.inventory.Inventory;
import neverless.domain.model.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.model.entity.mapobject.Player;
import neverless.context.EventContext;
import neverless.service.model.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private GameRepository gameRepository;
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
        inventory.getEquipment().setWeapon(newWeapon);
        inventory.getBag().remove(newWeapon);
        Player player = gameRepository.getPlayer();
        eventContext.addInventoryRightHandEquipEvent();
    }

    /**
     * Clears right hand. Current weapon in right hand goes to bag.
     */
    public void clearRightHand() {
        Inventory inventory = loadInventory();
        AbstractHandEquipment curWeapon = inventory.getEquipment().getWeapon();
        if (curWeapon != null) {
            inventory.getBag().addLast(curWeapon);
        }
        inventory.getEquipment().setWeapon(null);
    }

    /**
     * Loads player and returns his inventory.
     *
     * @return inventory.
     */
    private Inventory loadInventory() {
        Player player = gameRepository.getPlayer();
        return player.getInventory();
    }
}