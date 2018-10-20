package neverless.domain.dialog.quest;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.item.AbstractItem;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class QuestReward {

    private Integer reward;
    private Integer experience;
    private List<AbstractItem> rewardItems = new ArrayList<>();
}
