package neverless.domain.entity.item.weapon;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public class Sword extends AbstractMeleeWeapon {

}
