package neverless.domain.item.weapon;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.item.AbstractItem;

@Data
@Accessors(chain = true)
public abstract class AbstractWeapon extends AbstractItem {

    private String title;
    private Integer power;
}
