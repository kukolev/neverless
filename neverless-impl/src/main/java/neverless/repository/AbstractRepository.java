package neverless.repository;

import neverless.domain.entity.AbstractGameObject;
import neverless.domain.entity.GameObjectId;
import neverless.repository.util.InjectionUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AbstractRepository<V extends AbstractGameObject> extends JpaRepository<V, GameObjectId>, InjectionUtil {

    default V simpleGet(String uniqueName) {
        return getOne(new GameObjectId(uniqueName, getSessionUtil().getCurrentSessionId()));
    }

    default V simpleSave(V value) {
        if (value.getId().getSession() == null) {
            value.getId().setSession(getSessionUtil().getCurrentSessionId());
        }

        return save(value);
    }

    default List<V> findAllObjects() {
        return findAll();
    }
}