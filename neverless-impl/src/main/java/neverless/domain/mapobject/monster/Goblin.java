package neverless.domain.mapobject.monster;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public final class Goblin extends AbstractMonster {

    @Override
    public String getSignature() {
        return "GOBLIN_";
    }
}