package neverless.service.screendata;

import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class NpcService extends AbstractService {

    @Autowired
    private MapObjectsRepository mapObjectsRepository;

    public AbstractNpc getNpcAtPosition(int npcX, int npcY, String location) {
        AbstractMapObject object = mapObjectsRepository.findAllByLocation(location)
                .stream()
                .filter(o -> o.getX() == npcX && o.getY() == npcY)
                .findFirst()
                .get();
        return (AbstractNpc) object;
    }
}
