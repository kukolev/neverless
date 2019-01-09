package neverless.repository.persistence;

import neverless.domain.entity.inventory.Bag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BagRepository extends JpaRepository<Bag, String> {
}
