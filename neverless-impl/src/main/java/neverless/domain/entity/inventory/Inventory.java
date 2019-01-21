package neverless.domain.entity.inventory;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Inventory {

    private String id;
    private Bag bag;
    private Equipment equipment = new Equipment();
}
