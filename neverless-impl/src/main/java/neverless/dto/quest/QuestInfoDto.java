package neverless.dto.quest;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Dto for quest information.
 * Dto consists of main information about quest.
 */
@Data
@Accessors(chain = true)
public class QuestInfoDto {

    private QuestState state;
    private String title;
    private List<String> journal = new ArrayList<>();
}
