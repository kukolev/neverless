package neverless.domain.dialog;

import lombok.Data;

import lombok.experimental.Accessors;
import neverless.domain.dialog.predicate.NpcStartingPhrasePredicate;

import java.util.List;

@Data
@Accessors(chain = true)
public class NpcPhrase {

    private NpcStartingPhrasePredicate activator;
    private String phraseText;
    private List<PlayerPhrase> answers;
}
