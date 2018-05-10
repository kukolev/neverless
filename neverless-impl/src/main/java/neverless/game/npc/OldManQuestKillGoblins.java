package neverless.game.npc;

import neverless.domain.dialog.quest.AbstractQuest;
import neverless.domain.dialog.quest.QuestReward;
import neverless.domain.dialog.quest.QuestStep;
import neverless.repository.MapObjectsRepository;
import neverless.dto.screendata.quest.QuestState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static neverless.game.npc.OldMan.OLDMAN_ASK_QUEST;
import static neverless.game.npc.OldMan.OLDMAN_CHEATED;
import static neverless.game.npc.OldMan.OLDMAN_UNIQUENAME;

@Service
public class OldManQuestKillGoblins extends AbstractQuest {

    private QuestReward reward = new QuestReward()
            .setReward(100)
            .setExperience(550);
    @Autowired
    private MapObjectsRepository mapRepository;


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
                () -> getParamBool(OLDMAN_UNIQUENAME, OLDMAN_ASK_QUEST), stepQuestUnknown);

        QuestStep stepQuestDone = QuestStep.build(
                2,
                "Yes, I cheated Old dumb! It was so easy... ",
                QuestState.DONE,
                () -> getParamBool(OLDMAN_UNIQUENAME, OLDMAN_CHEATED), stepQuestAccepted);

        List<QuestStep> steps = new ArrayList<>();
        steps.add(stepQuestAccepted);
        steps.add(stepQuestDone);
        return steps;
    }

    @Override
    public QuestReward getReward() {
        return reward;
    }
}
