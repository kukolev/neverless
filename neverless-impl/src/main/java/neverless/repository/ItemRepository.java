package neverless.repository;

import neverless.domain.entity.item.AbstractItem;
import neverless.repository.util.InjectionUtil;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends AbstractRepository<AbstractItem>, InjectionUtil {
}
