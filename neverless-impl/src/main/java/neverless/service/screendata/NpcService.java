package neverless.service.screendata;

import neverless.domain.mapobject.AbstractMapObject;
import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NpcService extends AbstractService {

    @Autowired
    private MapObjectsRepository mapObjectsRepository;

    public AbstractNpc getNpcAtPosition(int npcX, int npcY) {
        AbstractMapObject object = mapObjectsRepository.findAll()
                .stream()
                .filter(o -> o.getX() == npcX && o.getY() == npcY)
                .findFirst()
                .get();
        return (AbstractNpc) object;
    }
}
