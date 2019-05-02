package neverless.domain.model.entity.item.civil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.item.AbstractItem;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AbstractCivilItem extends AbstractItem {
}
