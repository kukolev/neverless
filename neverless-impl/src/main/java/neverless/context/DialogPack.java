package neverless.context;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;

@Data
@Accessors(chain = true)
public class DialogPack {
    private Dialog dialog;
    private NpcPhrase npcPhrase;
}
