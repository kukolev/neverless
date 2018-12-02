package neverless.domain.entity.mapobject.respawn;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resources;
import neverless.domain.entity.mapobject.enemy.AbstractEnemyFactory;
import neverless.domain.entity.mapobject.enemy.GoblinFactory;

import javax.persistence.Entity;


@Data
@Accessors(chain = true)
@Entity
public class GoblinRespawnPoint extends AbstractRespawnPoint {

    /** {@inheritDoc} */
    @Override
    public Class<? extends AbstractEnemyFactory> getEnemyFactory() {
        return GoblinFactory.class;
    }

    /** {@inheritDoc} */
    @Override
    public String getSignature() {
        return Resources.IMG_RESPAWN_POINT;
    }
}
