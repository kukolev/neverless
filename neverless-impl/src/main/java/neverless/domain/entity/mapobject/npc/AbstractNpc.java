package neverless.domain.entity.mapobject.npc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.dialog.Dialog;
import neverless.domain.entity.mapobject.AbstractPhysicalObject;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractNpc extends AbstractPhysicalObject {

    private Dialog dialog = new Dialog();
}
