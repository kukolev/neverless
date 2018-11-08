package neverless.domain.entity.inventory;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Accessors(chain = true)
@Entity
public class Equipment {

    @Id
    private String id;

    @OneToOne
    private AbstractHandEquipment rightHand;

    @OneToOne
    private AbstractHandEquipment leftHand;
}