package neverless.domain.entity.item.weapon;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class AbstractMeleeWeapon extends AbstractHandEquipment {
}
