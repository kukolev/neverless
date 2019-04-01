package neverless.service.behavior;

import neverless.command.Command;
import neverless.command.CommandType;
import neverless.command.EnemyCommandFactory;
import neverless.command.enemy.EnemyMapGoPayload;
import neverless.command.enemy.EnemyWaitPayload;
import neverless.domain.entity.BehaviorState;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Direction;
import neverless.domain.entity.Location;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.context.EventContext;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.enemy.EnemyFactory;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.context.RequestContext;
import neverless.domain.event.AbstractEvent;
import neverless.domain.event.MapGoImpossibleEvent;
import neverless.repository.cache.GameCache;
import neverless.service.util.LocalMapService;
import neverless.util.CoordinateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.sin;
import static java.lang.StrictMath.cos;
import static neverless.Constants.DELTA_BETWEEN_NEAREST_PLATFORMS;
import static neverless.Constants.ENEMY_DEFAULT_WAIT_TIME;
import static neverless.Constants.ENEMY_DEFAULT_WALK_LENGTH;
import static neverless.Constants.MOVING_IN_DIRECTION_LIMIT;
import static neverless.util.CoordinateUtils.calcNextStep;
import static neverless.util.CoordinateUtils.isCoordinatesInRange;
import static neverless.util.CoordinateUtils.isCurvesIntersected;
import static neverless.util.CoordinateUtils.isSegmentAndCurveIntersected;

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
    private GameCache gameCache;
    @Autowired
    private EnemyCommandFactory commandFactory;

    @Override
    public BehaviorState onProcess(AbstractEnemy enemy) {
        BehaviorState newState = BehaviorState.IDLE;
        switch (enemy.getCommand().getCommandType()) {
            case ENEMY_WAIT:
                newState = performCommandWait(enemy, enemy.getCommand());
                break;
            case ENEMY_MOVE:
                newState = performCommandWalk(enemy, enemy.getCommand());
                break;
        }
        // calculate next command
        calcNewCommand(enemy);
        return newState;
    }

    private BehaviorState performCommandWait(AbstractEnemy enemy, Command command) {
        EnemyWaitPayload payload = (EnemyWaitPayload) command.getPayload();
        payload.setWaitTime(payload.getWaitTime() - 1);
        return BehaviorState.IDLE;
    }

    private BehaviorState performCommandWalk(AbstractEnemy enemy, Command command) {
        // todo: implement;
        EnemyMapGoPayload payload = (EnemyMapGoPayload) command.getPayload();
        Coordinate coordinate = calcNextStep(enemy.getX(), enemy.getY(), payload.getX(), payload.getY());
        if (localMapService.isPassable(enemy, coordinate.getX(), coordinate.getY())) {
            enemy.setX(coordinate.getX());
            enemy.setY(coordinate.getY());
            eventContext.addMapGoEvent(enemy.getUniqueName(), enemy.getX(), enemy.getY());
        } else {
            eventContext.addMapGoImpossibleEvent(enemy.getUniqueName());
        }
        return BehaviorState.MOVE;
    }

    /**
     * Initiates a moving/chasing/attacking for all enemies.
     */
    public void processBehavior() {
        // todo: refactor it using command approach
//        Player player = playerService.getPlayer();
//          player.getLocation().getRespawnPoints()
//                .forEach(rp -> {
//                    AbstractEnemy enemy = rp.getEnemy();
//                    if (enemy != null) {
//                        BehaviorState behaviorState = calcNewCommand(rp.getEnemy());
//                        enemy.setBehaviorState(behaviorState);
//                        switch (behaviorState) {
//                            case MOVE:
//                                walk(rp.getEnemy());
//                                break;
//                            case CHASE:
//                                chase(rp.getEnemy());
//                                break;
//                            case ATTACK:
//                                attack(rp.getEnemy());
//                                break;
//                        }
//                    }
//                });
    }

    /**
     * Calculates and sets new command to enemy.
     *
     * @param enemy enemy whose command should be calculated
     */
    private void calcNewCommand(AbstractEnemy enemy) {
        boolean isNeedToAttack = false; // todo: check range and if already attacking
        boolean isWaitingEnough = isWaitingEnough(enemy);
        boolean isEndOfMove = isEndOfMove(enemy);

        Command command = enemy.getCommand();
        if (isNeedToAttack) {
            command = commandFactory.createEnemyAttackCommand();
        } else if (isWaitingEnough) {
            Random random = new Random(System.currentTimeMillis());

            double alfa = random.nextInt(628) / 100.0;
            int tryCount = 0;
            boolean canGo;
            int newX;
            int newY;

            do {
                alfa = alfa + tryCount * 0.01;
                newX = enemy.getX() + (int) (ENEMY_DEFAULT_WALK_LENGTH * cos(alfa));
                newY = enemy.getY() + (int) (ENEMY_DEFAULT_WALK_LENGTH * sin(alfa));

                canGo = isCanGoNewPosition(enemy, newX, newY);
                tryCount++;
            } while (!(canGo || tryCount == 628));

            if (canGo) {
                command = commandFactory.createEnemyMapGoCommand(newX, newY);
            }
        } else if (isEndOfMove) {
            command = commandFactory.createEnemyWaitCommand(ENEMY_DEFAULT_WAIT_TIME);
        }
        enemy.setCommand(command);
    }

    private boolean isCanGoNewPosition(AbstractEnemy enemy, int newX, int newY) {
        List<AbstractMapObject> objects = enemy.getLocation().getObjects();
        for(AbstractMapObject object: objects) {
            if (isSegmentAndCurveIntersected(enemy.getX(), enemy.getY(), newX, newY, object.getPlatformCoordinates())) {
                return false;
            }
        }
        return true;
    }

    private boolean isAggressiveRange(AbstractEnemy enemy) {
        Player player = gameCache.getPlayer();
        return isCoordinatesInRange(player.getX(), player.getY(), enemy.getX(), enemy.getY(), enemy.getAgrRange());
    }

    private boolean isWaitingEnough(AbstractEnemy enemy) {
        if (enemy.getCommand().getCommandType() == CommandType.ENEMY_WAIT) {
            EnemyWaitPayload payload = (EnemyWaitPayload) enemy.getCommand().getPayload();
            return payload.getWaitTime() <= 0;
        }
        return false;
    }

    private boolean isEndOfMove(AbstractEnemy enemy) {
        if (enemy.getCommand().getCommandType() == CommandType.ENEMY_MOVE) {
            EnemyMapGoPayload payload = (EnemyMapGoPayload) enemy.getCommand().getPayload();
            boolean isArrived = payload.getX() == enemy.getX() && payload.getY() == enemy.getY();
            boolean isCantMove = isCanMove(enemy);
            return isArrived || !isCantMove;
        }
        return false;
    }

    private boolean isCanMove(AbstractEnemy enemy) {
        List<AbstractEvent> events = eventContext.getEvents(enemy.getUniqueName());
        return !events.stream()
                .anyMatch(e -> e instanceof MapGoImpossibleEvent);
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
        Player player = gameCache.getPlayer();

        // calculate radiuses for player and enemy ellipses
        // new radiuses should be a bit wider by a little delta
        int newPlayerRadiusX = (player.getPlatformWidth() / 2) + DELTA_BETWEEN_NEAREST_PLATFORMS;
        int newPlayerRadiusY = (player.getPlatformHeight() / 2) + DELTA_BETWEEN_NEAREST_PLATFORMS;
        int newEnemyRadiusX = (enemy.getPlatformWidth() / 2) + DELTA_BETWEEN_NEAREST_PLATFORMS;
        int newEnemyRadiusY = (enemy.getPlatformHeight() / 2) + DELTA_BETWEEN_NEAREST_PLATFORMS;

        // main idea - if two ellipses with radius + delta are intersected
        // then two ellipses are close enough for attack
        return isCurvesIntersected(
                player.getX(),
                player.getY(),
                newPlayerRadiusX,
                newPlayerRadiusY,
                enemy.getX(),
                enemy.getY(),
                newEnemyRadiusX,
                newEnemyRadiusY);
    }

    /**
     * Calculates and returns next coordinate in LoS between player and enemy.
     *
     * @param enemy enemy.
     */
    private Coordinate getNextCoordinatesForLos(AbstractEnemy enemy) {
        Player player = gameCache.getPlayer();
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
            Player player = gameCache.getPlayer();
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
        Player player = gameCache.getPlayer();

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

        EnemyFactory factory = getEnemyFactory(respawnPoint);
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
    private EnemyFactory getEnemyFactory(AbstractRespawnPoint respawnPoint) {
        Class<? extends EnemyFactory> factoryClass = respawnPoint.getEnemyFactory();
        return applicationContext.getBean(factoryClass);
    }
}