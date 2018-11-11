package neverless.domain.entity.item;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.AbstractGameObject;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractItem extends AbstractGameObject {

    @Column
    private String title;
}

