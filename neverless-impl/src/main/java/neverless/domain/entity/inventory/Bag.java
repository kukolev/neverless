package neverless.domain.entity.inventory;

import lombok.Data;
import neverless.domain.entity.item.AbstractItem;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


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
