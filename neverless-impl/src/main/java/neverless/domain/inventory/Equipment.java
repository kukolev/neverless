package neverless.domain.inventory;

import lombok.Data;
import neverless.domain.item.weapon.AbstractWeapon;

@Data
public class Equipment {

    private AbstractWeapon rightHand;
    private AbstractWeapon leftHand;
}
