package neverless.domain.entity.mapobject.enemy;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resouces;

import javax.persistence.Entity;


@Data
@Accessors(chain = true)
@Entity
public final class Goblin extends AbstractEnemy {

    @Override
    public String getSignature() {
        return Resouces.IMG_GOBLIN;
    }
}