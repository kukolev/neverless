package neverless.domain.quest;

import neverless.domain.quest.AbstractQuest;
import neverless.game.npc.OldManQuestKillGoblins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestContainer {

    private List<AbstractQuest> questList = new ArrayList<>();

    public void add(AbstractQuest quest) {
        questList.add(quest);
    }

    public List<AbstractQuest> findAll() {
        return questList;
    }

    public AbstractQuest finaById(String questId) {
        // todo: throw exception when nothing is found
        return questList.stream()
                .filter(q -> q.getQuestId().equals(questId))
                .findFirst()
                .orElse(null);
    }
}