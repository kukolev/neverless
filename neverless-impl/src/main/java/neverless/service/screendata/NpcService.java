package neverless.service.screendata;

import neverless.domain.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.npc.AbstractNpc;
import org.springframework.stereotype.Service;

@Service
public class NpcService {

    public AbstractNpc getNpcAtPosition(int npcX, int npcY, Location location) {
        // todo: throw exception when nothing is found
        AbstractMapObject object = location.getObjects()
                .stream()
                .filter(o -> o.getX() == npcX && o.getY() == npcY)
                .findFirst()
                .orElse(null);
        return (AbstractNpc) object;
    }
}
