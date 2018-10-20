package neverless.service.screendata;

import neverless.domain.inventory.Inventory;
import neverless.domain.item.civil.AbstractCivilItem;
import neverless.domain.item.AbstractItem;
import neverless.domain.item.weapon.AbstractWeapon;
import neverless.domain.mapobject.Player;
import neverless.dto.screendata.inventory.InventoryScreenDataDto;
import neverless.dto.screendata.inventory.ItemDto;
import neverless.dto.screendata.inventory.WeaponDto;
import neverless.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class InventoryService {

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Equips weapon in right hand.
     * Item added to right hand and current weapon in right hand goes to bag.
     *
     * @param weapon
     */
    public void equipRightHand(AbstractWeapon weapon) {
        Inventory inventory = loadInventory();
        inventory.getBag().putLast(weapon);
        inventory.getEquipment().setRightHand(weapon);
    }

    /**
     * Equips weapon in left hand.
     * Item added to left hand and current weapon in left hand goes to bag.
     *
     * @param weapon
     */
    public void equipLeftHand(AbstractWeapon weapon) {
        Inventory inventory = loadInventory();
        inventory.getBag().putLast(weapon);
        inventory.getEquipment().setRightHand(weapon);
    }

    public InventoryScreenDataDto getScreenData() {
        InventoryScreenDataDto screenData = new InventoryScreenDataDto();
        Inventory inventory = loadInventory();

        // Right and left hands
        screenData.setRightHand(mapWeaponToDto(inventory.getEquipment().getRightHand()));
        screenData.setLeftHand(mapWeaponToDto(inventory.getEquipment().getLeftHand()));

        // Items in the bag
        Map<Integer, WeaponDto> weaponsDto = new HashMap<>();
        Map<Integer, ItemDto> itemsDto = new HashMap<>();
        Set<Map.Entry<Integer, AbstractItem>> items = inventory.getBag().getItems().entrySet();
        items.forEach(e -> {
                    if (e.getValue() instanceof AbstractCivilItem) {
                        ItemDto dto = mapItemToDto((AbstractCivilItem) e.getValue());
                        itemsDto.put(e.getKey(), dto);
                    }
                    if (e.getValue() instanceof AbstractWeapon) {
                        WeaponDto dto = mapWeaponToDto((AbstractWeapon) e.getValue());
                        weaponsDto.put(e.getKey(), dto);
                    }
                });
        screenData.setWeapons(weaponsDto);
        screenData.setItems(itemsDto);

        return screenData;
    }

    private WeaponDto mapWeaponToDto(AbstractWeapon weapon) {
        if (weapon == null) return new WeaponDto();
        return new WeaponDto()
                .setTitle(weapon.getTitle())
                .setPower(weapon.getPower());
    }

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
        Player player = playerRepository.get();
        return player.getInventory();
    }
}