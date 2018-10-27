package neverless.game.quest;

import neverless.domain.dialog.quest.AbstractQuest;
import neverless.game.npc.OldManQuestKillGoblins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestContainer {

    @Autowired
    private ApplicationContext context;

    private List<AbstractQuest> questList = new ArrayList<>();

    @PostConstruct
    public void init() {
        questList.add(context.getBean(OldManQuestKillGoblins.class));
    }

    public List<AbstractQuest> findAll() {
        return questList;
    }

    public AbstractQuest finaById(String questId) {
        return questList.stream()
                .filter(q -> q.getQuestId().equals(questId))
                .findFirst()
                .get();
    }
}