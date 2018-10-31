package neverless.repository;

import neverless.domain.AbstractGameObject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static neverless.Constants.CURRENT_SESSION_ID;

@Repository
public abstract class AbstractRepository<V extends AbstractGameObject> {
    private Map<String, V> map = new ConcurrentHashMap<>();

    public V get(String uniqueName) {
        String key = getCurrentSessionId() + "_" +  uniqueName;
        return map.get(key);
    }

    public void save(V value) {
        String key = getCurrentSessionId() + "_" + value.getUniqueName();
        map.put(key, value);
    }

    public List<V> findAllObjects() {
        return new ArrayList<>(map.values());
    }

    private String getCurrentSessionId() {
        // todo: instead constant we should return id of player has been logon.
        return CURRENT_SESSION_ID;
    }
}