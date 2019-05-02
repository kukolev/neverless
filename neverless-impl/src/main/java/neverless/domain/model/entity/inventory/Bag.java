package neverless.domain.model.entity.inventory;

import lombok.Data;
import neverless.domain.model.entity.item.AbstractItem;
import neverless.domain.model.entity.item.weapon.AbstractHandEquipment;

import java.util.ArrayList;
import java.util.List;

@Data
public class Bag {

    private List<AbstractItem> items = new ArrayList<>();

    public void addLast(AbstractItem item) {
        items.add(item);
    }

    public void remove(AbstractItem item) {
        items.remove(item);
    }

    public AbstractHandEquipment getWeaponByNumber(Integer itemNo) {
        return (AbstractHandEquipment) items.get(itemNo);
    }
}
