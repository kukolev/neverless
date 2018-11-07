package neverless.domain.mapobject.respawn;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.monster.AbstractEnemyFactory;
import neverless.domain.mapobject.monster.GoblinFactory;

import javax.persistence.Entity;


@Data
@Accessors(chain = true)
@Entity
public class GoblinRespawnPoint extends AbstractRespawnPoint {

    /** {@inheritDoc} */
    @Override
    AbstractEnemyFactory getEnemyFactory() {
        return new GoblinFactory();
    }

    @Override
    public String getSignature() {
        return "RESPAWN_GOBLINS_";
    }
}
