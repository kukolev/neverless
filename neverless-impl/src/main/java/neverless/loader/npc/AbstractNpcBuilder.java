package neverless.loader.npc;

import neverless.domain.game.dialog.Dialog;
import neverless.domain.game.mapobject.npc.AbstractNpc;
import neverless.domain.repository.MapObjectsRepository;

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
