package neverless.game.enemy;

import neverless.domain.model.entity.inventory.Money;
import neverless.domain.model.entity.mapobject.loot.AbstractLootFactory;
import neverless.domain.model.entity.mapobject.loot.LootContainer;
import org.springframework.stereotype.Component;

@Component
public class GoblinLootFactory extends AbstractLootFactory {

    @Override
    public LootContainer createLoot() {
        LootContainer lootContainer = new LootContainer();
        Money money = new Money();
        money.inc(100);
        lootContainer.getItems().add(money);
        return lootContainer;
    }
}
