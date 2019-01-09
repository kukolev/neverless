package neverless.domain.entity.item.weapon;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.item.AbstractItem;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public abstract class AbstractHandEquipment extends AbstractItem {

    @Column
    private Integer power;
}
