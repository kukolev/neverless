package neverless.domain.entity.mapobject.npc;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import neverless.domain.dialog.Dialog;
import neverless.domain.entity.mapobject.AbstractMapObject;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Accessors(chain = true)
@Entity
public abstract class AbstractNpc extends AbstractMapObject {

    @Setter
    @Getter
    @Transient
    private Dialog dialog = new Dialog();

    public AbstractNpc() {
        initProperties();
        initDialog(dialog);
    }

    protected abstract void initProperties();

    protected abstract void initDialog(Dialog dialog);
}
