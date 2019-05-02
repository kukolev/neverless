package neverless.domain.model.dialog;

import lombok.Data;

import lombok.experimental.Accessors;
import neverless.domain.model.dialog.predicate.NpcStartingPhrasePredicate;

import java.util.List;

@Data
@Accessors(chain = true)
public class NpcPhrase {

    private NpcStartingPhrasePredicate activator;
    private String phraseText;
    private List<PlayerPhrase> answers;
}
