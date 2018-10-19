package neverless.game.npc;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.event.AbstractDialogEvent;
import neverless.domain.dialog.predicate.NpcStartingPhrasePredicate;
import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.util.logger.DialogBuilder;

import static neverless.util.logger.DialogBuilder.ALWAYS_AVAILABLE;


@Data
@Accessors(chain = true)
public final class OldMan extends AbstractNpc {

    public static final String OLDMAN_UNIQUENAME = "Old Man";
    public static final String OLDMAN_ASK_QUEST = "OLDMAN_ASK_QUEST";
    public static final String OLDMAN_CHEATED = "OLDMAN_CHEATED";
    public static final String OLDMAN_SIGNATURE = "OLDMAN_MAGE_";

    @Override
    protected void initProperties() {
        setX(51);
        setY(51);
        setUniqueName(OLDMAN_UNIQUENAME);
        setHeight(1);
        setWidth(1);
        setSignature(OLDMAN_SIGNATURE);
    }

    @Override
    protected void initDialog(Dialog dialog) {
        // Events
        AbstractDialogEvent evtQuestGiven = () -> this.setParam(OLDMAN_ASK_QUEST, true);
        AbstractDialogEvent evtQuestDone = () -> this.setParam(OLDMAN_CHEATED, true);

        // Predicates
        NpcStartingPhrasePredicate isFirstMeeting = () -> !this.getParamBool(OLDMAN_ASK_QUEST);
        NpcStartingPhrasePredicate isSecondMeeting = () -> this.getParamBool(OLDMAN_ASK_QUEST);

        DialogBuilder builder = new DialogBuilder(dialog);
        builder.create("Hello, adventurer!", isFirstMeeting)
                .addPlayerAnswer(1, "Hello, Old Man, can you give me some job?")
                .addNpcAnswer(2, "Of course! Kill all goblins near the river")
                .addPlayerAnswer(3, "Okey!", ALWAYS_AVAILABLE, evtQuestGiven)
                .addPlayerAnswer(3, "No, it's not for me")
                .addPlayerAnswer(1, "Go away, old dumb!")
                .addNpcAnswer(2, "Mister, please, return and help me...")
                .addPlayerAnswer(3, "Maybe in future...")
                .addPlayerAnswer(3, "No.");

        builder.create("Hello Again, what about my quest?", isSecondMeeting)
                .addPlayerAnswer(1, "Hmm... no idea")
                .addPlayerAnswer(1, "In progress")
                .addPlayerAnswer(1, "(Cheat) Quest completed", ALWAYS_AVAILABLE, evtQuestDone);
    }
}