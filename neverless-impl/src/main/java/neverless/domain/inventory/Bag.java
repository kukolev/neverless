package neverless.domain.inventory;

import lombok.Data;
import neverless.domain.item.AbstractItem;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Data
public class Bag {

    private Map<Integer, AbstractItem> items = new TreeMap<>();

    public void putLast(AbstractItem item) {
        Integer maxKey = getLastKey();
        items.put(maxKey + 1, item);
    }

    private Integer getLastKey() {
        Set<Integer> keys = items.keySet();
        return keys.stream()
                .max(Integer::compareTo)
                .get();
    }
}
