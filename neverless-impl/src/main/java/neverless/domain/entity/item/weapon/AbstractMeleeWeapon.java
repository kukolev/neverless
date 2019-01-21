package neverless.domain.entity.item.weapon;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AbstractMeleeWeapon extends AbstractHandEquipment {
}
