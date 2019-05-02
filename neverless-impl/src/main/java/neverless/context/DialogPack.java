package neverless.context;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.model.dialog.Dialog;
import neverless.domain.model.dialog.NpcPhrase;

@Data
@Accessors(chain = true)
public class DialogPack {
    private Dialog dialog;
    private NpcPhrase npcPhrase;
}
