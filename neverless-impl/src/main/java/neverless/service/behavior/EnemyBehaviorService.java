package neverless.service.behavior;

import neverless.domain.entity.mapobject.Direction;
import neverless.domain.entity.Location;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.domain.entity.mapobject.BehaviorStage;
import neverless.context.EventContext;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.enemy.AbstractEnemyFactory;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.context.RequestContext;
import neverless.service.util.LocalMapService;
import neverless.util.CoordinateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static neverless.Constants.DELTA_BETWEEN_NEAREST_PLATFORMS;
import static neverless.Constants.MOVING_IN_DIRECTION_LIMIT;
import static neverless.util.CoordinateUtils.isCoordinatesInRange;
import static neverless.util.CoordinateUtils.isCurvesIntersected;

@Service
public class EnemyBehaviorService extends AbstractBehaviorService<AbstractEnemy> {

    @Autowired
    private RequestContext requestContext;
    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private PlayerBehaviorService playerService;

    @Override
    public void processObject(AbstractEnemy object) {
        //todo: implement
    }

    /**
     * Initiates a moving/chasing/attacking for all enemies.
     */
    public void processBehavior() {
        // todo: refactor it using command approach
        Player player = playerService.getPlayer();
        player.getLocation().getRespawnPoints()
                .forEach(rp -> {
                    AbstractEnemy enemy = rp.getEnemy();
                    if (enemy != null) {
                        BehaviorStage behaviorStage = calcBehavior(rp.getEnemy());
                        enemy.setBehaviorStage(behaviorStage);
                        switch (behaviorStage) {
                            case WALKING:
                                walk(rp.getEnemy());
                                break;
                            case CHASING:
                                chase(rp.getEnemy());
                                break;
                            case ATTACKING:
                                attack(rp.getEnemy());
                                break;
                        }
                    }
                });
    }

    /**
     * Returns behaviorStage, calculated for enemy.
     *
     * @param enemy enemy whose behaviorStage should be calculated
     */
    private BehaviorStage calcBehavior(AbstractEnemy enemy) {
        Player player = playerService.getPlayer();
        boolean playerIsNear = isPlayerNear(enemy);
        boolean playerIsInAggressiveRange = isCoordinatesInRange(player.getX(), player.getY(), enemy.getX(), enemy.getY(), enemy.getAgrRange());

        if (playerIsInAggressiveRange) {
            if (playerIsNear) {
                return BehaviorStage.ATTACKING;
            } else {
                return BehaviorStage.CHASING;
            }
        } else {
            return BehaviorStage.WALKING;
        }
    }


    /**
     * Changes position of an enemy in normal mode.
     * Enemy should processBehavior near it's respawn point.
     *
     * @param enemy enemy that should walking in quite manner.
     */
    private void walk(AbstractEnemy enemy) {
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
        } while (needNewDirection && tryCount <= Direction.values().length);
    }

    /**
     * Returns true if enemy performed successfully moving to his previous direction.
     * Method tries to perform moving if possible.
     *
     * @param enemy walking enemy.
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
    private void chase(AbstractEnemy enemy) {
        Coordinate coordinate = getNextCoordinatesForLos(enemy);
        if (localMapService.isPassable(enemy, coordinate.getX(), coordinate.getY())) {
            enemy
                    .setX(coordinate.getX())
                    .setY(coordinate.getY());
        }
    }

    /**
     * Returns true if enemy position canProcessObject close to player.
     *
     * @param enemy checked enemy.
     */
    private boolean isPlayerNear(AbstractEnemy enemy) {
        Player player = playerService.getPlayer();

        // calculate radiuses for player and enemy ellipses
        // new radiuses should be a bit wider by a little delta
        int newPlayerRadiusX = (player.getPlatformWidth() / 2) + DELTA_BETWEEN_NEAREST_PLATFORMS;
        int newPlayerRadiusY = (player.getPlatformHeight() / 2) + DELTA_BETWEEN_NEAREST_PLATFORMS;
        int newEnemyRadiusX = (enemy.getPlatformWidth() / 2) + DELTA_BETWEEN_NEAREST_PLATFORMS;
        int newEnemyRadiusY = (enemy.getPlatformHeight() / 2) + DELTA_BETWEEN_NEAREST_PLATFORMS;

        // main idea - if two ellipses with radius + delta are intersected
        // then two ellipses are close enough for attack
        return isCurvesIntersected(
                player.getX() + player.getPlatformCenterX(),
                player.getY() + player.getPlatformCenterY(),
                newPlayerRadiusX,
                newPlayerRadiusY,
                enemy.getX() + enemy.getPlatformCenterX(),
                enemy.getY() + enemy.getPlatformCenterY(),
                newEnemyRadiusX,
                newEnemyRadiusY);
    }

    /**
     * Calculates and returns next coordinate in LoS between player and enemy.
     *
     * @param enemy enemy.
     */
    private Coordinate getNextCoordinatesForLos(AbstractEnemy enemy) {
        Player player = playerService.getPlayer();
        int playerX = player.getX();
        int playerY = player.getY();
        int enemyX = enemy.getX();
        int enemyY = enemy.getY();

        return CoordinateUtils.calcNextStep(playerX, playerY, enemyX, enemyY);
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
     * Respawn point canProcessObject able to recreate an enemy if there no live enemy in the respawn point.
     */
    public void respawn() {
        Player player = playerService.getPlayer();

        List<AbstractRespawnPoint> points = player.getLocation().getRespawnPoints();
        points.forEach(p -> {

            // Guarantee that new enemy would not be created
            // until current canProcessObject alive
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
        // todo: position should be random
        newEnemy.setX(respawnPoint.getX());
        newEnemy.setY(respawnPoint.getY());
        newEnemy.setBornX(newEnemy.getX());
        newEnemy.setBornY(newEnemy.getY());
        newEnemy.setAreaX(respawnPoint.getAreaX());
        newEnemy.setAreaY(respawnPoint.getAreaY());
        newEnemy.setRespawnPoint(respawnPoint);

        newEnemy.setLocation(location);
        location.getObjects().add(newEnemy);
        return newEnemy;
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
}