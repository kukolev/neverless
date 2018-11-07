package neverless.domain.item.weapon;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.item.AbstractItem;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractHandEquipment extends AbstractItem {

    @Column
    private Integer power;
}
