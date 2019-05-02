package neverless.service.model.util;

import neverless.domain.model.entity.Location;
import neverless.domain.model.entity.mapobject.AbstractPhysicalObject;
import neverless.domain.model.entity.mapobject.npc.AbstractNpc;
import org.springframework.stereotype.Service;

@Service
public class NpcService {

    public AbstractNpc getNpcAtPosition(int npcX, int npcY, Location location) {
        // todo: throw exception when nothing is found
        AbstractPhysicalObject object = location.getObjects()
                .stream()
                .filter(o -> o.getX() == npcX && o.getY() == npcY)
                .findFirst()
                .orElse(null);
        return (AbstractNpc) object;
    }
}
