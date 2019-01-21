package neverless.domain.entity.inventory;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;

@Data
@Accessors(chain = true)
public class Equipment {
    private String id;
    private AbstractHandEquipment rightHand;
    private AbstractHandEquipment leftHand;
}