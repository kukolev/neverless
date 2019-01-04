package neverless.service.screendata;

import neverless.Direction;
import neverless.domain.Location;
import neverless.dto.CoordinateDto;
import neverless.context.EventContext;
import neverless.domain.entity.item.weapon.AbstractMeleeWeapon;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.enemy.AbstractEnemyFactory;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.dto.enemy.EnemyDto;
import neverless.dto.enemy.EnemyScreenDataDto;
import neverless.dto.inventory.WeaponDto;
import neverless.context.RequestContext;
import neverless.repository.EnemyRepository;
import neverless.repository.ItemRepository;
import neverless.util.CoordinateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;
import static neverless.Constants.MOVING_IN_DIRECTION_LIMIT;
import static neverless.util.CoordinateUtils.isCoordinatesInRange;

@Service
@Transactional
public class EnemyService {

    @Autowired
    private RequestContext requestContext;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private EnemyRepository enemyRepository;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private PlayerService playerService;

    /**
     * Initiates a moving/attacking for all enemies.
     */
    public void move() {
        Player player = playerService.getPlayer();
        player.getLocation().getRespawnPoints()
                .forEach(rp -> {
                    if (rp.getEnemy() != null) {
                        if (!(walk(rp.getEnemy()) || chase(rp.getEnemy()))) {
                            attack(rp.getEnemy());
                        }
                    }
                });
    }

    /**
     * Changes position of an enemy in normal mode.
     * Enemy should move near it's respawn point.
     *
     * @param enemy enemy that should walking in quite manner.
     */
    private boolean walk(AbstractEnemy enemy) {

        Player player = playerService.getPlayer();
        boolean chaseOrAttack = isCoordinatesInRange(player.getX(), player.getY(), enemy.getX(), enemy.getY(), enemy.getAgrRange());

        if (!chaseOrAttack) {
            boolean needNewDirection;
            int tryCount = 0;
            Random random = new Random(System.currentTimeMillis());

            do {
                needNewDirection = !walkInPreviousDirection(enemy);
                if (needNewDirection) {
                    int directionInd = random.nextInt(Direction.values().length);
                    Direction newDirection = Direction.values()[directionInd];
                    if (newDirection != enemy.getWalkDirection()) {
                        enemy
                                .setWalkDirection(newDirection)
                                .setWalkTime(0);
                        tryCount++;
                    }
                }

            } while(needNewDirection && tryCount <= Direction.values().length);

            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if enemy performed successfully moving to his previous direction.
     * Method tries to perform moving if possible.
     *
     * @param enemy     walking enemy.
     */
    private boolean walkInPreviousDirection(AbstractEnemy enemy) {

        // false if we are walking in same direction more then should
        if (enemy.getWalkTime() >= MOVING_IN_DIRECTION_LIMIT) {
            return false;
        }

        int newX = enemy.getX();
        int newY = enemy.getY();
        int speed = enemy.getSpeed();

        switch (enemy.getWalkDirection()) {
            case DOWN: {
                newY += speed;
                break;
            }
            case UP: {
                newY -= speed;
                break;
            }
            case RIGHT: {
                newX += speed;
                break;
            }
            case LEFT: {
                newX -= speed;
                break;
            }
            case DOWN_RIGHT: {
                newX += speed;
                newY += speed;
                break;
            }
            case DOWN_LEFT: {
                newX -= speed;
                newY += speed;
                break;
            }
            case UP_RIGHT: {
                newX += speed;
                newY -= speed;
                break;
            }
            case UP_LEFT: {
                newX -= speed;
                newY -= speed;
                break;
            }
        }

        if (isPossibleToWalk(enemy, newX, newY)) {
            enemy
                    .setX(newX)
                    .setY(newY);
            int walkTime = enemy.getWalkTime();
            walkTime++;
            enemy.setWalkTime(walkTime);

            return true;
        }
        return false;
    }

    /**
     * Returns true if enemy can go to the new coordinates.
     *
     * @param enemy walking enemy.
     * @param newX  new horizontal coordinate.
     * @param newY  new vertical coordinate.
     */
    private boolean isPossibleToWalk(AbstractEnemy enemy, int newX, int newY) {
        boolean isPassable = (localMapService.isPassable(enemy, newX, newY));
        boolean isNear =
                (newX <= enemy.getBornX() + enemy.getAreaX())
                        && (newY <= enemy.getBornY() + enemy.getAreaY())
                        && (newX >= enemy.getBornX() - enemy.getAreaX())
                        && (newY >= enemy.getBornY() - enemy.getAreaY());
        return (isNear && isPassable);
    }

    /**
     * Changes position in aggressive mode.
     * Enemy should chase the player.
     * Returns true if chases. Returns false if should attack.
     *
     * @param enemy enemy that should chasing the player.
     */
    private boolean chase(AbstractEnemy enemy) {
        if (!isCanAttack(enemy)) {
            CoordinateDto coordinate = getNextCoordinatesForLos(enemy);
            if (localMapService.isPassable(enemy, coordinate.getX(), coordinate.getY())) {
                enemy
                        .setX(coordinate.getX())
                        .setY(coordinate.getY());
            }
            return true;
        }
        return false;
    }

    /**
     * Calculates and returns if enemy is able to attack player.
     *
     * @param enemy enemy
     */
    private boolean isCanAttack(AbstractEnemy enemy) {
        return isPlayerNear(enemy);
    }

    /**
     * Returns true if enemy position is close to player.
     *
     * @param enemy checked enemy.
     */
    private boolean isPlayerNear(AbstractEnemy enemy) {
        Player player = playerService.getPlayer();
        int deltaX = abs(enemy.getX() - player.getX());
        int deltaY = abs(enemy.getY() - player.getY());
        return (deltaX <= 1) && (deltaY <= 1);
    }

    /**
     * Calculates and returns next coordinate in LoS between player and enemy.
     *
     * @param enemy enemy.
     */
    private CoordinateDto getNextCoordinatesForLos(AbstractEnemy enemy) {
        Player player = playerService.getPlayer();
        int playerX = player.getX();
        int playerY = player.getY();
        int enemyX = enemy.getX();
        int enemyY = enemy.getY();

        return CoordinateUtils.getNextCoordinatesForLos(playerX, playerY, enemyX, enemyY);
    }

    /**
     * Attacks the player
     *
     * @param enemy enemy that should attack the player.
     */
    private void attack(AbstractEnemy enemy) {
        if (calcToHit(enemy)) {
            int damage = calcDamage(enemy);
            Player player = playerService.getPlayer();
            player.decreaseHitPoints(damage);
            eventContext.addFightingEnemyHitEvent(enemy.getUniqueName(), damage);
        } else {
            eventContext.addFightingEnemyMissEvent(enemy.getUniqueName());
        }
    }

    /**
     * Calculates and returns if enemy hits the player.
     *
     * @param enemy enemy that tries tu hit the player.
     */
    private boolean calcToHit(AbstractEnemy enemy) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextBoolean();
    }

    /**
     * Calculates and returns damage, impacted by enemy.
     *
     * @param enemy enemy that attacks.
     */
    private int calcDamage(AbstractEnemy enemy) {
        AtomicInteger damage = new AtomicInteger();
        enemy.getWeapons()
                .forEach(w -> damage.addAndGet(w.getPower()));
        return damage.get();
    }

    /**
     * Creates enemies, related to respawn points.
     * Respawn point is able to recreate an enemy if there no live enemy in the respawn point.
     */
    public void respawn() {
        Player player = playerService.getPlayer();

        List<AbstractRespawnPoint> points = player.getLocation().getRespawnPoints();
        points.forEach(p -> {

            // Guarantee that new enemy would not be created
            // until current is alive
            if (p.getEnemy() != null) {
                p.setLastTurnInLife(requestContext.getTurnNumber());
            }
            int delta = requestContext.getTurnNumber() - p.getLastTurnInLife();

            // Calculates if new enemy should be respawn
            if (delta > p.getRespawnPeriod()) {
                if (p.getEnemy() == null) {
                    AbstractEnemy enemy = respawn(p, p.getLocation());
                    p.setEnemy(enemy);
                    p.setLastTurnInLife(requestContext.getTurnNumber());
                }
            }
        });
    }

    /**
     * Creates new enemy, associated with respawn point.
     *
     * @param respawnPoint respawn point that generates an enemy.
     */
    private AbstractEnemy respawn(AbstractRespawnPoint respawnPoint, Location location) {

        AbstractEnemyFactory factory = getEnemyFactory(respawnPoint);
        AbstractEnemy newEnemy = factory.create();
        AbstractEnemy result = enemyRepository.save(newEnemy);
        // todo: position should be random
        result.setX(respawnPoint.getX());
        result.setY(respawnPoint.getY());
        result.setBornX(result.getX());
        result.setBornY(result.getY());
        result.setAreaX(respawnPoint.getAreaX());
        result.setAreaY(respawnPoint.getAreaY());
        result.getWeapons()
                .forEach(itemRepository::save);
        result.setRespawnPoint(respawnPoint);

        result.setLocation(location);
        location.getObjects().add(result);
        return result;
    }

    /**
     * Returns an enemy factory, specified for a particular respawn point from application context.
     *
     * @param respawnPoint respawn point for which enemy factory should be returned.
     */
    private AbstractEnemyFactory getEnemyFactory(AbstractRespawnPoint respawnPoint) {
        Class<? extends AbstractEnemyFactory> factoryClass = respawnPoint.getEnemyFactory();
        return applicationContext.getBean(factoryClass);
    }

    /**
     * Returns list of enemies in the player's location
     */
    public EnemyScreenDataDto getScreenData() {
        EnemyScreenDataDto screenDataDto = new EnemyScreenDataDto();
        Player player = playerService.getPlayer();

        // find all enemies on sane location with player
        List<AbstractEnemy> enemies = player.getLocation()
                .getRespawnPoints()
                .stream()
                .map(AbstractRespawnPoint::getEnemy)
                .filter(Objects::nonNull)
                .collect(toList());

        enemies.forEach(e -> {
            EnemyDto enemyDto = new EnemyDto()
                    .setHealthPoints(e.getHitPoints())
                    .setUniqueName(e.getUniqueName())
                    .setName(e.getUniqueName())
                    .setX(e.getX())
                    .setY(e.getY());

            List<WeaponDto> weaponDtos = e.getWeapons()
                    .stream()
                    .map(this::mapWeaponToDto)
                    .collect(toList());

            enemyDto.setWeapons(weaponDtos);
            screenDataDto.getEnemies().add(enemyDto);
        });
        return screenDataDto;
    }

    /**
     * Converts domain weapon to DTO.
     *
     * @param meleeWeapon melee weapon that should be converted.
     */
    private WeaponDto mapWeaponToDto(AbstractMeleeWeapon meleeWeapon) {
        return new WeaponDto()
                .setPower(meleeWeapon.getPower())
                .setTitle(meleeWeapon.getTitle());
    }
}