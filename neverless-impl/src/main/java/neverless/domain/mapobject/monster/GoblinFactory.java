package neverless.domain.mapobject.monster;

public class GoblinFactory extends AbstractEnemyFactory {

    @Override
    public AbstractMonster create() {
        return new Goblin();
    }
}
