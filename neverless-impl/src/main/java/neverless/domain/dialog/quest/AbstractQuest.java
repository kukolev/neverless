package neverless.domain.dialog.quest;

import neverless.domain.mapobject.AbstractMapObject;
import neverless.repository.MapObjectsRepository;
import neverless.dto.screendata.quest.QuestState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public abstract class AbstractQuest {

    @Autowired
    private MapObjectsRepository repository;

    public abstract String getTitle();

    public abstract List<QuestStep> getSteps();

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

    public Boolean getParamBool(String objName, String paramName) {
        AbstractMapObject object = repository.get(objName);
        return object.getParamBool(paramName);
    }

    public Integer getParamInt(String objName, String paramName) {
        AbstractMapObject object = repository.get(objName);
        return object.getParamInt(paramName);
    }

    public String getParamStr(String objName, String paramName) {
        AbstractMapObject object = repository.get(objName);
        return object.getParamStr(paramName);
    }
}
