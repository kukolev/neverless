package neverless.dto.inventory;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class InventoryScreenDataDto {

    private WeaponDto rightHand = new WeaponDto();
    private WeaponDto leftHand = new WeaponDto();

    private Map<Integer, WeaponDto> weapons = new HashMap<>();
    private Map<Integer, ItemDto> items = new HashMap<>();
}
