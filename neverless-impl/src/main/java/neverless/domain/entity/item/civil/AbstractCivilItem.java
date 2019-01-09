package neverless.domain.entity.item.civil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.item.AbstractItem;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class AbstractCivilItem extends AbstractItem {
}
