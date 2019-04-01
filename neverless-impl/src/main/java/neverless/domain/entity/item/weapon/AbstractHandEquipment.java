package neverless.domain.entity.item.weapon;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.item.AbstractItem;

import static neverless.Constants.WEAPON_DEFAULT_RANGE;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractHandEquipment extends AbstractItem {
    private int power;
    private int range = WEAPON_DEFAULT_RANGE;
}
