package neverless.service;

import neverless.domain.mapobject.AbstractMapObject;
import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;
import neverless.exception.InvalidCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapObjectsHelper {

    @Autowired
    private MapObjectsRepository repository;

    public AbstractNpc getNpcAtPosition(int x, int y) {
        return (AbstractNpc) getMapObjectAtPosition(x, y);
    }

    public boolean isPassable(int x, int y) {
        AbstractMapObject mapObject = getMapObjectAtPosition(x, y);
        return (mapObject == null || mapObject.isPassable());
    }

    public AbstractMapObject getMapObjectAtPosition(int x, int y) {
        return repository.findAll()
                .stream()
                .filter(object -> (object.getX() == x) && (object.getY() == y))
                .findFirst()
                .orElse(null);
    }
}