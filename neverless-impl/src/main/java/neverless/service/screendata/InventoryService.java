package neverless.service.screendata;

import neverless.domain.entity.inventory.Inventory;
import neverless.domain.entity.item.civil.AbstractCivilItem;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.entity.mapobject.Player;
import neverless.dto.inventory.InventoryScreenDataDto;
import neverless.dto.inventory.ItemDto;
import neverless.dto.inventory.WeaponDto;
import neverless.context.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InventoryService {

    @Autowired
    private PlayerService playerService;
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
        eventContext.addInventoryRightHandEquipEvent();
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
        eventContext.addInventoryLeftHandEquipEvent();
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
     * Returns screen data, presents current state of the inventory.
     */
    public InventoryScreenDataDto getScreenData() {
        InventoryScreenDataDto screenData = new InventoryScreenDataDto();
        Inventory inventory = loadInventory();

        // Right and left hands
        screenData.setRightHand(mapWeaponToDto(inventory.getEquipment().getRightHand()));
        screenData.setLeftHand(mapWeaponToDto(inventory.getEquipment().getLeftHand()));

        // Items in the bag
        Map<Integer, WeaponDto> weaponsDto = new HashMap<>();
        Map<Integer, ItemDto> itemsDto = new HashMap<>();
        AtomicInteger counter = new AtomicInteger(-1);
        inventory.getBag().getItems()
            .forEach(item -> {
                    int index = counter.incrementAndGet();
                    if (item instanceof AbstractCivilItem) {
                        ItemDto dto = mapItemToDto((AbstractCivilItem) item);
                        itemsDto.put(index, dto);
                    }
                    if (item instanceof AbstractHandEquipment) {
                        WeaponDto dto = mapWeaponToDto((AbstractHandEquipment) item);
                        weaponsDto.put(index, dto);
                    }
                });
        screenData.setWeapons(weaponsDto);
        screenData.setItems(itemsDto);

        return screenData;
    }

    /**
     * Maps and returns DTO for weapon.
     * @param weapon    weapon that should be mapped to DTO.
     */
    private WeaponDto mapWeaponToDto(AbstractHandEquipment weapon) {
        if (weapon == null) return new WeaponDto();
        return new WeaponDto()
                .setTitle(weapon.getTitle())
                .setPower(weapon.getPower());
    }

    /**
     * Maps and returns DTO for item.
     * @param item  item that should be mapped to DTO.
     */
    private ItemDto mapItemToDto(AbstractCivilItem item) {
        if (item == null) return new ItemDto();
        return new ItemDto()
                .setTitle(item.getTitle());
    }

    /**
     * Loads player and returns his inventory.
     *
     * @return inventory.
     */
    private Inventory loadInventory() {
        Player player = playerService.getPlayer();
        return player.getInventory();
    }
}