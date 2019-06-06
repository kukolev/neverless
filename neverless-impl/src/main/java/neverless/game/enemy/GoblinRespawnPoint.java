package neverless.game.enemy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemyFactory;
import neverless.domain.model.entity.mapobject.loot.AbstractLootFactory;
import neverless.domain.model.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.game.Signatures;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoblinRespawnPoint extends AbstractRespawnPoint {

    private AbstractLootFactory lootFactory;

    public GoblinRespawnPoint(AbstractLootFactory lootFactory) {
        this.lootFactory = lootFactory;
    }

    /** {@inheritDoc} */
    @Override
    public AbstractEnemyFactory getEnemyFactory() {
        return new GoblinFactory();
    }

    public AbstractLootFactory getLootFactory() {
        return lootFactory;
    }

    /** {@inheritDoc} */
    @Override
    public String getSignature() {
        return Signatures.IMG_RESPAWN_POINT;
    }
}
