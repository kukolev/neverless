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

    public PlayerAttackCommand createPlayerAttackCommand(AbstractEnemy enemy) {
        Player player = gameContext.getPlayer();
        return new PlayerAttackCommand()
                .setEnemy(enemy)
                .setEventContext(eventContext)
                .setLocalMapService(localMapService)
                .setPlayer(player);
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
        return new PlayerPortalEnterCommand()
                .setPlayer(player)
                .setEventContext(eventContext);
    }
}
