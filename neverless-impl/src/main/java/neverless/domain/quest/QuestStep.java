package neverless.domain.quest;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.quest.QuestState;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
public class QuestStep {

    private List<QuestStep> prevSteps;
    private QuestState state;
    private int stepOrder;
    private String journalNote;
    private QuestStepPredicate predicate;

    /**
     * Calculates and returns true/false if this step is available.
     * Step is available when available any of previous steps AND available this step.
     *
     * @return calculated availability.
     */
    public final Boolean isAvailable() {

        Boolean isPrev = prevSteps.isEmpty();
        for(QuestStep step: prevSteps) {
            isPrev = isPrev || step.isAvailable();
        }
        isPrev = isPrev && predicate.isAvailable();
        return isPrev;
    }

    public static QuestStep build(int stepOrder,
                                  String journalNote,
                                  QuestState state,
                                  QuestStepPredicate predicate,
                                  QuestStep ... prevSteps) {
        QuestStep questStep = new QuestStep();
        questStep.setStepOrder(stepOrder);
        questStep.setJournalNote(journalNote);
        questStep.setState(state);
        questStep.setPredicate(predicate);
        questStep.setPrevSteps(Arrays.asList(prevSteps));

        return questStep;
    }
}
