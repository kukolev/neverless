package neverless.repository;

import neverless.domain.entity.GameObjectId;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnemyRepository extends JpaRepository<AbstractEnemy, GameObjectId> {

    /**
     * Returns all enemies on particular location.
     *
     * @param location  location from which enemies should be returned.
     */
    List<AbstractEnemy> findAllByLocation(String location);
}
