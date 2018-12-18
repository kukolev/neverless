package neverless.service.screendata;

import neverless.domain.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.npc.AbstractNpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class NpcService {

    public AbstractNpc getNpcAtPosition(int npcX, int npcY, Location location) {
        AbstractMapObject object = location.getObjects()
                .stream()
                .filter(o -> o.getX() == npcX && o.getY() == npcY)
                .findFirst()
                .get();
        return (AbstractNpc) object;
    }
}
