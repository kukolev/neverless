package neverless.domain.item.civil;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.item.AbstractItem;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public class AbstractCivilItem extends AbstractItem {
}
