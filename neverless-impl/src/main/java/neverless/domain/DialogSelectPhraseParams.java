package neverless.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DialogSelectPhraseParams extends AbstractCommandParams {

    private int phraseNumber;
}
