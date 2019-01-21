package neverless.service.behavior;

import neverless.command.player.FightingAttackCommand;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.domain.entity.mapobject.Direction;
import neverless.command.AbstractCommand;
import neverless.command.MapGoCommand;
import neverless.context.EventContext;
import neverless.domain.entity.Game;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.repository.persistence.MapObjectsRepository;
import neverless.service.util.GameService;
import neverless.service.util.LocalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static neverless.util.CoordinateUtils.calcNextStep;

@Service
public class PlayerBehaviorService extends AbstractBehaviorService<Player> {

    @Autowired
    private GameService gameService;
    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private MapObjectsRepository mapObjectsRepository;
    @Autowired
    private EventContext eventContext;

    public Player getPlayer() {
        Game game = gameService.getGame();
        return game.getPlayer();
    }

    @Override
    public void processObject(Player player) {

        AbstractCommand abstractCommand = player.getCommand();
        if (abstractCommand instanceof MapGoCommand) {
            performCommand((MapGoCommand) abstractCommand);
        } else if (abstractCommand instanceof FightingAttackCommand) {
            performCommand((FightingAttackCommand) abstractCommand);
        }
    }

    private void performCommand(MapGoCommand mapGoCommand) {
        Player player = getPlayer();
        Coordinate coordinate = calcNextStep(player.getX(), player.getY(), mapGoCommand.getX(), mapGoCommand.getY());
        if (localMapService.isPassable(player, coordinate.getX(), coordinate.getY())) {
            player.setX(coordinate.getX());
            player.setY(coordinate.getY());
            eventContext.addMapGoEvent(player.getUniqueName(), Direction.DOWN);
        } else {
            eventContext.addMapGoImpossibleEvent();
        }
    }

    private void performCommand(FightingAttackCommand fightingAttackCommand) {
        AbstractEnemy enemy = fightingAttackCommand.getEnemy();
        if (calcToHit(enemy)) {
            // Player hits.
            int damage = calcDamage(enemy);
            enemy.decreaseHitPoints(damage);
            if (enemy.getHitPoints() <= 0) {
                killEnemy(enemy);
                eventContext.addFightingEnemyKillEvent(enemy.getUniqueName());
            } else {
                eventContext.addFightingPlayerHitEvent(enemy.getUniqueName(), damage);
            }
        } else
        {
            // Player misses.
            eventContext.addFightingPlayerMissEvent(enemy.getUniqueName());
        }
    }

    public void doPortalEnter(String portalId) {
        // todo: fix it.
        AbstractMapObject object = mapObjectsRepository.getOne(portalId);
        AbstractPortal portal = object instanceof AbstractPortal ? (AbstractPortal) object : null;
        if (portal == null) {
            // todo: throw concrete exception here;
            throw new IllegalArgumentException();
        }

        Player player = getPlayer();
        player
                .setLocation(portal.getDestination())
                .setX(portal.getDestX())
                .setY(portal.getDestY());
        eventContext.addPortalEnterEvent(portal.getDestination().getTitle());
    }

    /**
     * Calculates and returns true if player damage enemy.
     *
     * @param enemy enemy attacked by the player.
     */
    private boolean calcToHit(AbstractEnemy enemy) {
        // todo: implement real calculation.
        Random random = new Random(System.currentTimeMillis());
        return random.nextBoolean();
    }

    /**
     * Calculates and returns damage, impacted by the player during attack to enemy.
     *
     * @param enemy enemy attacked by the player.
     */
    private int calcDamage(AbstractEnemy enemy) {
        Player player = getPlayer();
        AbstractHandEquipment rightWeapon =  player.getInventory().getEquipment().getRightHand();
        AbstractHandEquipment leftWeapon =  player.getInventory().getEquipment().getLeftHand();

        int rightDamage = rightWeapon != null ? rightWeapon.getPower() : 0;
        int leftDamage = leftWeapon != null ? leftWeapon.getPower() : 0;

        return (rightDamage + leftDamage);
    }

    /**
     * Kills an enemy and performs needed actions.
     *
     * @param enemy an enemy that should die.
     */
    private void killEnemy(AbstractEnemy enemy) {
        AbstractRespawnPoint respawnPoint = enemy.getRespawnPoint();
        respawnPoint.setEnemy(null);
        enemy.getLocation().getObjects().remove(enemy);
    }
}