package neverless.domain.model.entity.inventory;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.item.weapon.AbstractHandEquipment;

@Data
@Accessors(chain = true)
public class Equipment {
    private String id;
    private AbstractHandEquipment weapon;
}