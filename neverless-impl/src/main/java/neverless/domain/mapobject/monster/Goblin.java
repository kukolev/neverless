package neverless.domain.mapobject.monster;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;


@Data
@Accessors(chain = true)
@Entity
public final class Goblin extends AbstractEnemy {

    @Override
    public String getSignature() {
        return "GOBLIN_";
    }
}