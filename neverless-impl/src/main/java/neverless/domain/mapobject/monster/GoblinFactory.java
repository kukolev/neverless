package neverless.domain.mapobject.monster;

public class GoblinFactory extends AbstractEnemyFactory {

    @Override
    public AbstractEnemy create() {
        return new Goblin();
    }
}
