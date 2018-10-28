package neverless.domain.inventory;

import lombok.Data;
import neverless.domain.item.weapon.AbstractHandEquipment;

@Data
public class Equipment {

    private AbstractHandEquipment rightHand;
    private AbstractHandEquipment leftHand;
}