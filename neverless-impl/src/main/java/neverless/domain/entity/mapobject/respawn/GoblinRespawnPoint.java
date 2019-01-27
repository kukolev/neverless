package neverless.domain.entity.mapobject.respawn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Resources;
import neverless.domain.entity.mapobject.enemy.EnemyFactory;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoblinRespawnPoint extends AbstractRespawnPoint {

    /** {@inheritDoc} */
    @Override
    public Class<? extends EnemyFactory> getEnemyFactory() {
        return EnemyFactory.class;
    }

    /** {@inheritDoc} */
    @Override
    public String getSignature() {
        return Resources.IMG_RESPAWN_POINT;
    }
}
