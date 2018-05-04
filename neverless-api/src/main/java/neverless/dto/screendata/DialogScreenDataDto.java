package neverless.dto.screendata;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class DialogScreenDataDto {

    /** Text of NPC's phrase */
    private String npcPhrase;

    /** List of answers*/
    private List<String> answers = new ArrayList<>();
}
