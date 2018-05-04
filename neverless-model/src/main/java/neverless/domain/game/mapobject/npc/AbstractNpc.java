package neverless.domain.game.mapobject.npc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import neverless.domain.game.dialog.Dialog;
import neverless.domain.game.mapobject.AbstractMapObject;

@Accessors(chain = true)
public abstract class AbstractNpc extends AbstractMapObject {

    @Setter
    @Getter
    private Dialog dialog;
}
