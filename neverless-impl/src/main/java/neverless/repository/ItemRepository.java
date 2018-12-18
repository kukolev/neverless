package neverless.repository;

import neverless.domain.entity.item.AbstractItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<AbstractItem, String> {
}
