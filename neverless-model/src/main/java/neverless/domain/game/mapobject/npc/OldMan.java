package neverless.domain.game.mapobject.npc;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public final class OldMan extends AbstractNpc {

    public OldMan() {
        setHeight(1);
        setWidth(1);
        setSignature("OLDMAN_MAGE_");
    }
}
