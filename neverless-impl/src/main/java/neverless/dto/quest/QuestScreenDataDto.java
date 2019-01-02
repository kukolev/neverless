package neverless.dto.quest;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class QuestScreenDataDto {

    private List<QuestInfoDto> inProgress = new ArrayList<>();
    private List<QuestInfoDto> done = new ArrayList<>();
    private List<QuestInfoDto> failed = new ArrayList<>();
}
