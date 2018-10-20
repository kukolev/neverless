package neverless.domain.item;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.AbstractGameObject;

@Data
@Accessors(chain = true)
public abstract class AbstractItem extends AbstractGameObject {

    private String title;
}

