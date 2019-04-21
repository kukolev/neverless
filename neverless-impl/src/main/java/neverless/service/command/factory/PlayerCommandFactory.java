package neverless.service.command.factory;

import neverless.service.command.impl.PlayerAttackCommand;
import neverless.service.command.impl.PlayerContinueCommand;
import neverless.service.command.impl.PlayerMapGoCommand;
import neverless.service.command.impl.PlayerPortalEnterCommand;
import neverless.context.EventContext;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.portal.LocationPortal;
import neverless.context.GameContext;
import neverless.service.util.CombatService;
import neverless.service.util.LocalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandFactory {

    @Autowired
    private EventContext eventContext;
    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private GameContext gameContext;
    @Autowired
    private CombatService combatService;

    public PlayerAttackCommand createPlayerAttackCommand(AbstractEnemy enemy) {
        Player player = gameContext.getPlayer();
        return new PlayerAttackCommand(enemy, player, localMapService, eventContext, combatService);
    }

    public PlayerMapGoCommand createPlayerMapGoCommand(int x, int y) {
        Player player = gameContext.getPlayer();
        return new PlayerMapGoCommand()
                .setX(x)
                .setY(y)
                .setEventContext(eventContext)
                .setLocalMapService(localMapService)
                .setPlayer(player);
    }

    public PlayerContinueCommand createPlayerContinueCommand() {
        return new PlayerContinueCommand();
    }

    public PlayerPortalEnterCommand createPlayerPortalEnterCommand(LocationPortal portal) {
        Player player = gameContext.getPlayer();
        return new PlayerPortalEnterCommand(player, eventContext, portal, localMapService);
    }
}
