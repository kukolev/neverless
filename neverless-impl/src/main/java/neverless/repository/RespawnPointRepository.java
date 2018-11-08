package neverless.repository;

import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.repository.util.InjectionUtil;
import org.springframework.stereotype.Repository;

@Repository
public interface RespawnPointRepository extends AbstractRepository<AbstractRespawnPoint>, InjectionUtil {
}
