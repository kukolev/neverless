package neverless.domain.mapobject.monster;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public final class Goblin extends AbstractMonster {

    public Goblin() {
        setHeight(1);
        setWidth(1);
        setSignature("GOBLIN_");
    }
}