package neverless.domain.entity.mapobject.enemy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Resources;

import javax.persistence.Entity;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public final class Goblin extends AbstractEnemy {

    @Override
    public String getSignature() {
        return Resources.IMG_GOBLIN;
    }
}