package neverless.game.npc;

import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.event.AbstractDialogEvent;
import neverless.domain.dialog.predicate.NpcStartingPhrasePredicate;
import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.domain.mapobject.npc.OldMan;
import neverless.util.logger.AbstractNpcBuilder;
import neverless.util.logger.DialogBuilder;

import static neverless.util.logger.DialogBuilder.ALWAYS_AVAILABLE;

public class OldManBuilder extends AbstractNpcBuilder {

    public static final String OLDMAN_UNIQUENAME = "Old Man";
    public static final String OLDMAN_ASK_QUEST = "OLDMAN_ASK_QUEST";
    public static final String OLDMAN_CHEATED = "OLDMAN_CHEATED";


    @Override
    protected AbstractNpc initNpc() {
        OldMan npc = new OldMan();
        npc
                .setX(51)
                .setY(51)
                .setUniqueName(OLDMAN_UNIQUENAME);
        return npc;
    }

    @Override
    protected Dialog initDialog(AbstractNpc npc) {
        // Events
        AbstractDialogEvent evtQuestGiven = () -> npc.setParam(OLDMAN_ASK_QUEST, true);
        AbstractDialogEvent evtQuestDone = () -> npc.setParam(OLDMAN_CHEATED, true);


        // Predicates
        NpcStartingPhrasePredicate isFirstMeeting = () -> !npc.getParamBool(OLDMAN_ASK_QUEST);
        NpcStartingPhrasePredicate isSecondMeeting = () -> npc.getParamBool(OLDMAN_ASK_QUEST);


        Dialog dialog = new Dialog();
        DialogBuilder builder = new DialogBuilder(dialog);
        builder.create("Hello, adventurer!", isFirstMeeting)
                .addPlayerAnswer(1,"Hello, Old Man, can you give me some job?")
                    .addNpcAnswer(2,"Of course! Kill all goblins near the river")
                        .addPlayerAnswer(3, "Okey!", ALWAYS_AVAILABLE, evtQuestGiven)
                        .addPlayerAnswer(3, "No, it's not for me")
                .addPlayerAnswer(1, "Go away, old dumb!")
                    .addNpcAnswer(2, "Mister, please, return and help me...")
                        .addPlayerAnswer(3, "Maybe in future...")
                        .addPlayerAnswer(3, "No.");

        builder.create("Hello Again, what about my quest?", isSecondMeeting)
                .addPlayerAnswer(1,"Hmm... no idea")
                .addPlayerAnswer(1, "In progress")
                .addPlayerAnswer(1, "Quest completed", ALWAYS_AVAILABLE, evtQuestDone);


        return dialog;
    }
}

