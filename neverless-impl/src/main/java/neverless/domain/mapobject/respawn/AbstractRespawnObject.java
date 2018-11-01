package neverless.domain.mapobject.respawn;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.AbstractMapObject;
import neverless.domain.mapobject.monster.AbstractEnemyFactory;
import neverless.domain.mapobject.monster.AbstractMonster;

@Data
@Accessors(chain = true)
public abstract class AbstractRespawnObject extends AbstractMapObject {

    public final AbstractMonster respawnEnemy() {
        AbstractEnemyFactory factory = getEnemyFactory();
        return factory.create();
    }
    abstract AbstractEnemyFactory getEnemyFactory();
}
