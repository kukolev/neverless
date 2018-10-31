package neverless.repository;

import neverless.domain.mapobject.AbstractMapObject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MapObjectsRepository extends AbstractRepository<AbstractMapObject> {
    /**
     * Returns list of objects, placed on particular location
     *
     * @param location  location
     */
    public List<AbstractMapObject> findAllByLocation(String location) {
        return this.findAllObjects()
                .stream()
                .filter(o -> o.getLocation().equals(location))
                .collect(Collectors.toList());
    }
}
