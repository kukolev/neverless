package neverless.service.screendata;

import neverless.Direction;
import neverless.context.EventContext;
import neverless.domain.Game;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.dto.PlayerDto;
import neverless.dto.player.PlayerScreenDataDto;
import neverless.repository.persistence.MapObjectsRepository;
import neverless.repository.persistence.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private EnemyService enemyService;
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

    public void goOnDirection(Direction direction) {
        Player player = getPlayer();

        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case UP:
                newY -= LOCAL_MAP_STEP_LENGTH;
                break;
            case DOWN:
                newY += LOCAL_MAP_STEP_LENGTH;
                break;
            case LEFT:
                newX -= LOCAL_MAP_STEP_LENGTH;
                break;
            case RIGHT:
                newX += LOCAL_MAP_STEP_LENGTH;
                break;
            case UP_LEFT: {
                newY -= LOCAL_MAP_STEP_LENGTH;
                newX -= LOCAL_MAP_STEP_LENGTH;
                break;
            }
            case UP_RIGHT: {
                newY -= LOCAL_MAP_STEP_LENGTH;
                newX += LOCAL_MAP_STEP_LENGTH;
                break;
            }
            case DOWN_LEFT: {
                newY += LOCAL_MAP_STEP_LENGTH;
                newX -= LOCAL_MAP_STEP_LENGTH;
                break;
            }
            case DOWN_RIGHT: {
                newY += LOCAL_MAP_STEP_LENGTH;
                newX += LOCAL_MAP_STEP_LENGTH;
                break;
            }
        }
        goOnDirection(newX, newY, direction);
    }

    private void goOnDirection(int x, int y, Direction direction) {
        Player player = getPlayer();
        if (localMapService.isPassable(player, x, y)) {
            doMoving(player, x, y, direction);
            return;
        }
        doImpossibleMove();
    }

    private void doMoving(Player player, int x, int y, Direction direction) {
        player.setX(x);
        player.setY(y);
        playerRepository.save(player);
        eventContext.addMapGoEvent(direction);
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

    private void doImpossibleMove() {
        eventContext.addMapGoImpossibleEvent();
    }


    /**
     * Performs an attack to enemy.
     *
     * @param enemyId   id of the attacked enemy.
     */
    public void attack(String enemyId) {
        doAttackToEnemy(enemyId);
    }

    /**
     * Performs all orchestration of attack to enemy.
     *
     * @param enemyId   id of the attacked enemy.
     */
    private void doAttackToEnemy(String enemyId) {
        AbstractEnemy enemy = enemyService.findById(enemyId);
        if (calcToHit(enemy)) {
            // Player hits.
            int damage = calcDamage(enemy);
            enemy.decreaseHitPoints(damage);
            if (enemy.getHitPoints() <= 0) {
                killEnemy(enemy);
                eventContext.addFightingEnemyKillEvent(enemyId);
            } else {
                eventContext.addFightingPlayerHitEvent(enemyId, damage);
            }
        } else
        {
            // Player misses.
            eventContext.addFightingPlayerMissEvent(enemyId);
        }
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

    public PlayerScreenDataDto getScreenData() {
        long t = System.nanoTime();
        PlayerDto playerDto = new PlayerDto();
        PlayerScreenDataDto screenDataDto = new PlayerScreenDataDto();

        Player player = getPlayer();
        playerDto
                .setHealthPoints(player.getHitPoints())
                .setLocation(player.getLocation().getTitle())
                .setX(player.getX())
                .setY(player.getY())
                .setPlatformCenterX(player.getPlatformCenterX())
                .setPlatformCenterY(player.getPlatformCenterY());
        screenDataDto.setPlayerDto(playerDto);
        System.out.println("PlayerScreenData = " + (System.nanoTime() - t));
        return screenDataDto;
    }
}