package neverless.repository;

import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespawnPointRepository extends JpaRepository<AbstractRespawnPoint, String> {
}
