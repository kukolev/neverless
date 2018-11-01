package neverless.domain.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.inventory.Inventory;

@Data
@Accessors(chain = true)
public class Player extends AbstractMapObject {

    // Dialog state
    private Dialog dialog;
    private NpcPhrase npcPhrase;

    private Inventory inventory = new Inventory();

    @Override
    public String getSignature() {
        return "PLAYER_";
    }
}
