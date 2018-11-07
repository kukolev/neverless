package neverless.domain.item;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.AbstractGameObject;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractItem extends AbstractGameObject {

    private String title;
}

