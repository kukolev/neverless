package neverless.game.npc;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resources;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.event.AbstractDialogEvent;
import neverless.domain.dialog.predicate.NpcStartingPhrasePredicate;
import neverless.domain.entity.mapobject.npc.AbstractNpc;
import neverless.util.DialogBuilder;

import javax.persistence.Entity;

import static neverless.util.DialogBuilder.ALWAYS_AVAILABLE;


@Data
@Accessors(chain = true)
@Entity
public final class OldMan extends AbstractNpc {

    public static final String OLDMAN_ASK_QUEST = "OLDMAN_ASK_QUEST";
    public static final String OLDMAN_CHEATED = "OLDMAN_CHEATED";

    @Override
    public String getSignature() {
        return Resources.IMG_OLD_MAN;
    }
}