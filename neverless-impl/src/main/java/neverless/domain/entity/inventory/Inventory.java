package neverless.domain.entity.inventory;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Inventory {

    private Bag bag = new Bag();
    private Equipment equipment = new Equipment();
}
