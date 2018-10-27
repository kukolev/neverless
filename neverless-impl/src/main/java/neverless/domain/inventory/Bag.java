package neverless.domain.inventory;

import lombok.Data;
import neverless.domain.item.AbstractItem;
import neverless.domain.item.weapon.AbstractWeapon;

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

    public AbstractWeapon getWeaponByNumber(Integer itemNo) {
        return (AbstractWeapon) items.get(itemNo);
    }
}
