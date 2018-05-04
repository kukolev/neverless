package neverless.loader.npc;

import neverless.domain.game.dialog.Dialog;
import neverless.domain.game.dialog.event.AbstractDialogEvent;
import neverless.domain.game.dialog.predicate.NpcStartingPhrasePredicate;
import neverless.domain.game.mapobject.npc.AbstractNpc;
import neverless.domain.game.mapobject.npc.OldMan;
import neverless.loader.DialogBuilder;

import static neverless.util.Constants.ALWAYS_AVAILABLE;

public class OldManBuilder extends AbstractNpcBuilder {

    private static final String OLDMAN_UNIQUENAME = "Old Man";
    private static final String OLDMAN_ASK_QUEST = "OLDMAN_ASK_QUEST";

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
                .addPlayerAnswer(1, "In progress");

        return dialog;
    }
}

