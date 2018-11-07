package neverless.domain.item.weapon;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public class AbstractMeleeWeapon extends AbstractHandEquipment {
}
