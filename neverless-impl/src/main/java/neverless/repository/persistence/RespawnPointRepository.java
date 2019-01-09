package neverless.repository.persistence;

import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespawnPointRepository extends JpaRepository<AbstractRespawnPoint, String> {
}
