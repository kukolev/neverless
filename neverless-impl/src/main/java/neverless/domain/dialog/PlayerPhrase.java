package neverless.domain.dialog;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.dialog.event.AbstractDialogEvent;
import neverless.domain.dialog.predicate.AvailablePlayerPhrasePredicate;

@Data
@Accessors(chain = true)
public class PlayerPhrase {

    private String id;
    private AvailablePlayerPhrasePredicate activator;
    private AbstractDialogEvent answerEvent;
    private String phraseText;
    private NpcPhrase nextNpcPhrase;
}
