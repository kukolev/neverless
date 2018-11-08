package neverless.repository;

import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.repository.util.InjectionUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapObjectsRepository extends AbstractRepository<AbstractMapObject>, InjectionUtil {

    /**
     * Returns list of objects, placed on particular location
     *
     * @param location  location
     */
    List<AbstractMapObject> findAllByLocation(String location);
}