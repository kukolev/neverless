package neverless.domain.entity.item.weapon;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.item.AbstractItem;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractHandEquipment extends AbstractItem {
    private Integer power;
}
