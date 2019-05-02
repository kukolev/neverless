package neverless.domain.model.entity.item.weapon;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.item.AbstractItem;

import static neverless.util.Constants.WEAPON_DEFAULT_RANGE;
import static neverless.util.Constants.WEAPON_DEFAULT_SPEED;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractHandEquipment extends AbstractItem {
    private int power;
    private int range = WEAPON_DEFAULT_RANGE;
    private int speed = WEAPON_DEFAULT_SPEED;
}
