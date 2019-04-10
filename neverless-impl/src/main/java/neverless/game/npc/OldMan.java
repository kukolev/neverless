package neverless.game.npc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Signatures;
import neverless.domain.entity.mapobject.npc.AbstractNpc;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public final class OldMan extends AbstractNpc {

    public static final String OLDMAN_ASK_QUEST = "OLDMAN_ASK_QUEST";
    public static final String OLDMAN_CHEATED = "OLDMAN_CHEATED";

    @Override
    public String getSignature() {
        return Signatures.IMG_OLD_MAN;
    }
}