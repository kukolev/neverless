package neverless.repository.persistence;

import neverless.domain.entity.mapobject.AbstractMapObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapObjectsRepository extends JpaRepository<AbstractMapObject, String> {
}