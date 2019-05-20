package neverless.service.model.command.impl;

import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.loot.LootItem;
import neverless.service.model.command.AbstractCommand;
import neverless.service.model.util.LocalMapService;

import static neverless.util.Constants.LOOT_ITEM_DESTINATION;
import static neverless.util.CoordinateUtils.isCoordinatesInRange;

public class PlayerTakeLootCommand extends AbstractCommand {

    private Player player;
    private LootItem lootItem;
    private LocalMapService localMapService;

    public PlayerTakeLootCommand(Player player, LootItem lootItem, LocalMapService localMapService) {
        this.player = player;
        this.lootItem = lootItem;
        this.localMapService = localMapService;
    }

    @Override
    protected BehaviorState onExecute() {
        if (!isCoordinatesInRange(player.getX(), player.getY(), lootItem.getX(), lootItem.getY(), LOOT_ITEM_DESTINATION)) {
            localMapService.makeStep(player, lootItem.getX(), lootItem.getY());
        }
        return BehaviorState.MOVE;
    }

    @Override
    public boolean checkFinished() {
        return (isCoordinatesInRange(player.getX(), player.getY(), lootItem.getX(), lootItem.getY(), LOOT_ITEM_DESTINATION));
    }
}
