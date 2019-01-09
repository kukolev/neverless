package neverless.game.npc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Resources;
import neverless.domain.entity.mapobject.npc.AbstractNpc;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public final class OldMan extends AbstractNpc {

    public static final String OLDMAN_ASK_QUEST = "OLDMAN_ASK_QUEST";
    public static final String OLDMAN_CHEATED = "OLDMAN_CHEATED";

    @Override
    public String getSignature() {
        return Resources.IMG_OLD_MAN;
    }
}