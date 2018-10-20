package neverless.domain.inventory;

import lombok.Data;

@Data
public class Inventory {

    private Bag bag = new Bag();
    private Equipment equipment = new Equipment();
}
