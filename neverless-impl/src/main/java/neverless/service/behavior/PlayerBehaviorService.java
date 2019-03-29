package neverless.service.behavior;

import neverless.command.player.PlayerAttackPayload;
import neverless.command.player.PlayerEnterPortalPayload;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.command.Command;
import neverless.command.player.PlayerMapGoPayload;
import neverless.context.EventContext;
import neverless.domain.entity.Game;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.repository.cache.GameCache;
import neverless.service.util.LocalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static neverless.util.CoordinateUtils.calcNextStep;

@Service
public class PlayerBehaviorService extends AbstractBehaviorService<Player> {

    @Autowired
    private GameCache gameCache;
    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private EventContext eventContext;

    @Override
    public void processObject(Player player) {

        Command abstractCommand = player.getCommand();
        if (abstractCommand == null) {
            return;
        }

        switch (player.getCommand().getCommandType()) {
            case PLAYER_WALK:
                performCommandMapGo();
                break;
            case PLAYER_ATTACK:
                performCommandAttack();
                break;
        }
    }

    private void performCommandMapGo() {
        Player player = gameCache.getPlayer();
        Command command = player.getCommand();
        PlayerMapGoPayload payload = (PlayerMapGoPayload) command.getPayload();

        Coordinate coordinate = calcNextStep(player.getX(), player.getY(), payload.getX(), payload.getY());
        if (localMapService.isPassable(player, coordinate.getX(), coordinate.getY())) {
            player.setX(coordinate.getX());
            player.setY(coordinate.getY());
            eventContext.addMapGoEvent(player.getUniqueName(), player.getX(), player.getY());
        } else {
            eventContext.addMapGoImpossibleEvent(player.getUniqueName());
        }
    }

    private void performCommandAttack() {
        Player player = gameCache.getPlayer();
        Command command = player.getCommand();
        PlayerAttackPayload payload = (PlayerAttackPayload) command.getPayload();

        AbstractEnemy enemy = payload.getEnemy();
        if (calcToHit(enemy)) {
            // Player hits.
            int damage = calcDamage(enemy);
            enemy.decreaseHitPoints(damage);
            if (enemy.getHitPoints() <= 0) {
                killEnemy(enemy);
                eventContext.addFightingEnemyKillEvent(player.getUniqueName(), enemy.getUniqueName());
            } else {
                eventContext.addFightingPlayerHitEvent(player.getUniqueName(), enemy.getUniqueName(), damage);
            }
        } else
        {
            // Player misses.
            eventContext.addFightingPlayerMissEvent(player.getUniqueName(), enemy.getUniqueName());
        }
    }

    private void performCommandPortalEnter() {
        Player player = gameCache.getPlayer();
        Command command = player.getCommand();
        PlayerEnterPortalPayload payload = (PlayerEnterPortalPayload) command.getPayload();
        // todo: fix it.
        if (payload.getPortal() == null) {
            // todo: throw concrete exception here;
            throw new IllegalArgumentException();
        }

        player
                .setLocation(payload.getPortal().getDestination())
                .setX(payload.getPortal().getDestX())
                .setY(payload.getPortal().getDestY());
        eventContext.addPortalEnterEvent(player.getUniqueName(), payload.getPortal().getDestination().getTitle());
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
        Player player = gameCache.getPlayer();
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