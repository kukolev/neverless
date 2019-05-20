package neverless.service.model.command.factory;

import neverless.domain.model.entity.mapobject.loot.LootItem;
import neverless.service.model.command.impl.PlayerAttackCommand;
import neverless.service.model.command.impl.PlayerMapGoCommand;
import neverless.service.model.command.impl.PlayerPortalEnterCommand;
import neverless.context.EventContext;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.model.entity.mapobject.portal.LocationPortal;
import neverless.service.model.GameRepository;
import neverless.service.model.command.impl.PlayerTakeLootCommand;
import neverless.service.model.util.CombatService;
import neverless.service.model.util.LocalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandFactory {

    @Autowired
    private EventContext eventContext;
    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private CombatService combatService;

    public PlayerAttackCommand createPlayerAttackCommand(AbstractEnemy enemy) {
        Player player = gameRepository.getPlayer();
        return new PlayerAttackCommand(enemy, player, localMapService, eventContext, combatService);
    }

    public PlayerMapGoCommand createPlayerMapGoCommand(int x, int y) {
        Player player = gameRepository.getPlayer();
        return new PlayerMapGoCommand()
                .setX(x)
                .setY(y)
                .setEventContext(eventContext)
                .setLocalMapService(localMapService)
                .setPlayer(player);
    }

    public PlayerPortalEnterCommand createPlayerPortalEnterCommand(LocationPortal portal) {
        Player player = gameRepository.getPlayer();
        return new PlayerPortalEnterCommand(player, eventContext, portal, localMapService);
    }

    public PlayerTakeLootCommand createPlayerTakeLootCommand(LootItem lootItem) {
        Player player = gameRepository.getPlayer();
        return new PlayerTakeLootCommand(player, lootItem, localMapService);
    }
}
