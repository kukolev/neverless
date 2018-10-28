package neverless.domain.item.weapon;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.item.AbstractItem;

@Data
@Accessors(chain = true)
public abstract class AbstractHandEquipment extends AbstractItem {

    private String title;
    private Integer power;
}
