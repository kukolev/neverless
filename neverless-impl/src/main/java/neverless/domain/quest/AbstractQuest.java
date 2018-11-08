package neverless.domain.quest;

import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.repository.MapObjectsRepository;
import neverless.dto.screendata.quest.QuestState;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public abstract class AbstractQuest {

    @Autowired
    private MapObjectsRepository repository;
    @Autowired
    private SessionUtil sessionUtil;

    public abstract String getTitle();

    public abstract List<QuestStep> getSteps();

    /**
     * Returns a unique quest identifier. Identifier is class canonical name.
     * Identifier should not be persisted and should be used only in runtime.
     */
    public final String getQuestId() {
        return this.getClass().getCanonicalName();
    }

    /**
     * Calculates and returns list that represents quest journal.
     * Journal is a function of quest-state.
     */
    public final List<String> getJournal() {
        List<String> journal = new ArrayList<>();

        getSteps().stream()
                .sorted(Comparator.comparingInt(QuestStep::getStepOrder))
                .forEach(step -> {
                    if (step.isAvailable()) {
                        journal.add(step.getJournalNote());
                    }
                });
        return journal;
    }

    /**
     * Calculates and returns current state of the quest
     */
    public final QuestState getState() {
        QuestStep currentStep =  getSteps().stream()
                .sorted(Comparator.comparingInt(step -> -step.getStepOrder()))
                .filter(step -> step.isAvailable())
                .findFirst()
                .orElse(null);
        if (currentStep != null) {
            return currentStep.getState();
        }
        return QuestState.UNKNOWN;
    }

    public abstract QuestReward getReward();

    protected final Boolean getParamBool(String objName, String paramName) {
        AbstractMapObject object = repository.findById(sessionUtil.createId(objName)).get();
        return object.getParamBool(paramName);
    }

    public final Integer getParamInt(String objName, String paramName) {
        AbstractMapObject object = repository.findById(sessionUtil.createId(objName)).get();
        return object.getParamInt(paramName);
    }

    public final String getParamStr(String objName, String paramName) {
        AbstractMapObject object = repository.findById(sessionUtil.createId(objName)).get();
        return object.getParamStr(paramName);
    }

    /**
     * Calculates and returns md5 hash of full quest journal.
     * It is needed for trigger journal updating and events raising.
     */
    public String getJournalHash() {
        StringBuilder stringBuilder = new StringBuilder();
        this.getJournal()
                .forEach(s -> stringBuilder.append(s));
        return DigestUtils.md5Hex(stringBuilder.toString());
    }
}
