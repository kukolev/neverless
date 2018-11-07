package neverless.domain.inventory;

import lombok.Data;
import neverless.domain.item.AbstractItem;
import neverless.domain.item.weapon.AbstractHandEquipment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Entity
public class Bag {

    @Id
    private String id;

    @OneToMany
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
