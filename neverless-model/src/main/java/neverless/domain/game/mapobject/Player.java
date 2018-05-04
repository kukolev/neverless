package neverless.domain.game.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.game.dialog.Dialog;
import neverless.domain.game.dialog.NpcPhrase;

@Data
@Accessors(chain = true)
public class Player extends AbstractMapObject {

    // Dialog state
    private Dialog dialog;
    private NpcPhrase npcPhrase;

    public Player() {
        setWidth(1);
        setHeight(1);
        setSignature("PLAYER_");
    }
}
