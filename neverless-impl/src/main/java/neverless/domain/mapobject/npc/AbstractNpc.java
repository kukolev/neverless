package neverless.domain.mapobject.npc;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import neverless.domain.dialog.Dialog;
import neverless.domain.mapobject.AbstractMapObject;

@Accessors(chain = true)
public abstract class AbstractNpc extends AbstractMapObject {

    @Setter
    @Getter
    private Dialog dialog = new Dialog();

    public AbstractNpc() {
        initProperties();
        initDialog(dialog);
    }

    protected abstract void initProperties();

    protected abstract void initDialog(Dialog dialog);
}
