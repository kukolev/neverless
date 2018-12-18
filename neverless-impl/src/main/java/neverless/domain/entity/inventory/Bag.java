package neverless.domain.entity.inventory;

import lombok.Data;
import neverless.domain.entity.item.AbstractItem;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
public class Bag {

    @Id
    @GeneratedValue(generator = "generic-uuid")
    @GenericGenerator(name = "generic-uuid", strategy = "uuid")
    private String id;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
