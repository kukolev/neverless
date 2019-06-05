package neverless.domain.model.entity.inventory;

import lombok.Getter;
import neverless.domain.model.entity.item.AbstractItem;

public class Money extends AbstractItem {

    @Getter
    private int amount;

    public void inc(int delta) {
        amount += delta;
    }

    public void dec(int delta) {
        amount -= delta;
    }

    public void clear() {
        amount = 0;
    }

    public String getTitle() {
        return amount + " gold";
    }
}
