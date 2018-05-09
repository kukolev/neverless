package neverless.service;

import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;
import neverless.exception.InvalidCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NpcService {

    @Autowired
    private MapObjectsRepository repository;

    public AbstractNpc getNpcAtPosition(int x, int y) {
        return (AbstractNpc) repository.findAll()
                .stream()
                .filter(npc -> (npc.getX() == x) && (npc.getY() == y))
                .findFirst()
                .orElseThrow(InvalidCommandException::new);
    }
}