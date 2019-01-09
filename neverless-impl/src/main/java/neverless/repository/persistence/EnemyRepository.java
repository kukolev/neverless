package neverless.repository.persistence;

import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyRepository extends JpaRepository<AbstractEnemy, String> {
}

