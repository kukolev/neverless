package neverless.domain.mapobject.respawn;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.monster.AbstractEnemyFactory;
import neverless.domain.mapobject.monster.GoblinFactory;


@Data
@Accessors(chain = true)
public class GoblinRespawnObject extends AbstractRespawnObject {

    @Override
    AbstractEnemyFactory getEnemyFactory() {
        return new GoblinFactory();
    }

    @Override
    public String getSignature() {
        return "RESPAWN_OBJECT";
    }
}
