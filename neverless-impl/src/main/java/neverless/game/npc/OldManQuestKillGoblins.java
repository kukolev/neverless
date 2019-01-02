package neverless.game.npc;

import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.event.AbstractDialogEvent;
import neverless.domain.dialog.predicate.NpcStartingPhrasePredicate;
import neverless.domain.quest.AbstractQuest;
import neverless.domain.quest.QuestReward;
import neverless.domain.quest.QuestStep;
import neverless.dto.quest.QuestState;
import neverless.util.DialogBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static neverless.game.npc.OldMan.OLDMAN_ASK_QUEST;
import static neverless.game.npc.OldMan.OLDMAN_CHEATED;
import static neverless.util.DialogBuilder.ALWAYS_AVAILABLE;

@Service
public class OldManQuestKillGoblins extends AbstractQuest {

    private QuestReward reward = new QuestReward()
            .setReward(100)
            .setExperience(550);

    @Override
    public String getTitle() {
        return "Goblins near the river";
    }

    @Override
    public List<QuestStep> getSteps() {
        QuestStep stepQuestUnknown = QuestStep.build(
                0,
                "",
                QuestState.UNKNOWN,
                () -> true);

        QuestStep stepQuestAccepted = QuestStep.build(
                1,
                "I should go to the river, find goblins and kill'em all!",
                QuestState.ACCEPTED,
                () -> getParamBool(OLDMAN_ASK_QUEST),
                stepQuestUnknown);

        QuestStep stepQuestDone = QuestStep.build(
                2,
                "Yes, I cheated Old dumb! It was so easy... ",
                QuestState.DONE,
                () -> getParamBool(OLDMAN_CHEATED),
                stepQuestAccepted);

        List<QuestStep> steps = new ArrayList<>();
        steps.add(stepQuestAccepted);
        steps.add(stepQuestDone);
        return steps;
    }

    @Override
    public QuestReward getReward() {
        return reward;
    }

    public void initDialog(Dialog dialog) {
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
