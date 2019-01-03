package neverless.service.screendata;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;
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

            // 1. create list of ways where enemy can go
            List<CoordinateDto> coordinates = new ArrayList<>();
            addWalkDirection(enemy.getX() + 1, enemy.getY(), enemy, coordinates);
            addWalkDirection(enemy.getX() - 1, enemy.getY(), enemy, coordinates);
            addWalkDirection(enemy.getX(), enemy.getY() + 1, enemy, coordinates);
            addWalkDirection(enemy.getX(), enemy.getY() - 1, enemy, coordinates);

            if (coordinates.size() > 0) {
                // 2. choose random direction
                Random random = new Random(System.currentTimeMillis());
                int crdInd = random.nextInt(coordinates.size());
                CoordinateDto newCoordinate = coordinates.get(crdInd);

                // 3. set new platformCoordinates
                enemy.setX(newCoordinate.getX());
                enemy.setY(newCoordinate.getY());

                eventContext.addEnemyMoveEvent(enemy.getUniqueName());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates possibility of walk to new platformCoordinates and add the platformCoordinates in list;
     *
     * @param newX        new X coordinate for enemy
     * @param newY        new Y coordinate for enemy
     * @param enemy       enemy for which new platformCoordinates are calculated
     * @param coordinates list of new platformCoordinates
     */
    private void addWalkDirection(int newX, int newY, AbstractEnemy enemy, List<CoordinateDto> coordinates) {
        boolean isPassable = (localMapService.isPassable(enemy, newX, newY));
        boolean isNear =
                (newX <= enemy.getBornX() + enemy.getAreaX())
                        && (newY <= enemy.getBornY() + enemy.getAreaY())
                        && (newX >= enemy.getBornX() - enemy.getAreaX())
                        && (newY >= enemy.getBornY() - enemy.getAreaY());

        if (isNear && isPassable) {
            coordinates.add(new CoordinateDto()
                    .setX(newX)
                    .setY(newY));
        }
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
                .forEach(w -> {
                    damage.addAndGet(w.getPower());
                });
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
        result.setX(respawnPoint.getX() + 20);
        result.setY(respawnPoint.getY() + 20);
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