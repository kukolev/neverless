package neverless.domain.entity.mapobject.respawn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Resources;
import neverless.domain.entity.mapobject.enemy.AbstractEnemyFactory;
import neverless.domain.entity.mapobject.enemy.GoblinFactory;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
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
