package neverless.service.model.command.impl;

import neverless.core.inventory.InventoryPane;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.loot.LootContainer;
import neverless.service.model.command.AbstractCommand;
import neverless.service.model.util.LocalMapService;

import static neverless.util.Constants.LOOT_ITEM_DESTINATION;
import static neverless.util.CoordinateUtils.isCoordinatesInRange;

public class PlayerTakeLootCommand extends AbstractCommand {

    private Player player;
    private LootContainer lootContainer;
    private LocalMapService localMapService;
    private InventoryPane inventoryPane;

    public PlayerTakeLootCommand(Player player, LootContainer lootContainer, LocalMapService localMapService,
                                 InventoryPane inventoryPane) {
        this.player = player;
        this.lootContainer = lootContainer;
        this.localMapService = localMapService;
        this.inventoryPane = inventoryPane;
    }

    @Override
    protected BehaviorState onExecute() {
        if (!isCoordinatesInRange(player.getX(), player.getY(), lootContainer.getX(), lootContainer.getY(), LOOT_ITEM_DESTINATION)) {
            localMapService.makeStep(player, lootContainer.getX(), lootContainer.getY());
        }
        if (checkFinished()) {
            inventoryPane.init(lootContainer.getItems(), player.getInventory());
            if (inventoryPane.showModal()) {
                inventoryPane.copyToInventory(player.getInventory());
                inventoryPane.copyToLootItems(lootContainer.getItems());
            }
            return BehaviorState.IDLE;
        } else {
            return BehaviorState.MOVE;
        }
    }

    @Override
    public boolean checkFinished() {
        return (isCoordinatesInRange(player.getX(), player.getY(), lootContainer.getX(), lootContainer.getY(), LOOT_ITEM_DESTINATION));
    }
}
