package neverless.domain.inventory;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.item.weapon.AbstractHandEquipment;

import javax.persistence.*;

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