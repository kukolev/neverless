package neverless.domain.model.dialog;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.model.dialog.event.AbstractDialogEvent;
import neverless.domain.model.dialog.predicate.AvailablePlayerPhrasePredicate;

@Data
@Accessors(chain = true)
public class PlayerPhrase {

    private String id;
    private AvailablePlayerPhrasePredicate activator;
    private AbstractDialogEvent answerEvent;
    private String phraseText;
    private NpcPhrase nextNpcPhrase;
}
