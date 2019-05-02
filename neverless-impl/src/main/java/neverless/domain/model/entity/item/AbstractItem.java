package neverless.domain.model.entity.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.AbstractGameObject;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractItem extends AbstractGameObject {
    private String title;
}

