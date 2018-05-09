package neverless.util.logger;

import neverless.domain.dialog.Dialog;
import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;

public abstract class AbstractNpcBuilder {

    public void build(MapObjectsRepository mapObjRepository) {
        AbstractNpc npc = initNpc();
        Dialog dialog = initDialog(npc);
        npc.setDialog(dialog);
        mapObjRepository.save(npc);
    }

    abstract protected AbstractNpc initNpc();

    abstract protected Dialog initDialog(AbstractNpc npc);
}
