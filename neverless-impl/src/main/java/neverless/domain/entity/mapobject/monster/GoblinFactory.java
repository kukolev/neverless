package neverless.domain.entity.mapobject.monster;

public class GoblinFactory extends AbstractEnemyFactory {

    @Override
    public AbstractEnemy create() {
        return new Goblin();
    }
}
