package neverless.domain.model.entity.mapobject.npc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.dialog.Dialog;
import neverless.domain.model.entity.mapobject.AbstractPhysicalObject;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractNpc extends AbstractPhysicalObject {

    private Dialog dialog = new Dialog();
}
