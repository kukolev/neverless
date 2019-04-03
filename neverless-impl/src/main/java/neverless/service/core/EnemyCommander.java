package neverless.service.core;

import neverless.service.command.AbstractCommand;
import neverless.service.command.factory.EnemyCommandFactory;
import neverless.service.command.impl.EnemyMapGoCommand;
import neverless.service.command.impl.EnemyWaitCommand;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.domain.entity.BehaviorState;
import neverless.domain.entity.Game;
import neverless.domain.entity.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.enemy.EnemyFactory;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.domain.event.AbstractEvent;
import neverless.domain.event.MapGoImpossibleEvent;
import neverless.context.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

import static java.lang.Math.sin;
import static java.lang.StrictMath.cos;
import static neverless.Constants.ENEMY_DEFAULT_WAIT_TIME;
import static neverless.Constants.ENEMY_DEFAULT_WALK_LENGTH;
import static neverless.util.CoordinateUtils.isCoordinatesInRange;
import static neverless.util.CoordinateUtils.isSegmentAndCurveIntersected;

@Component
public class EnemyCommander {

    @Autowired
    private GameContext gameContext;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private EnemyCommandFactory commandFactory;


    public void processEnemies() {
        respawn();
        Game game = gameContext.getGame();
        game.getLocations().forEach(location ->
                location.getRespawnPoints().forEach(rp -> {
                    AbstractEnemy enemy = rp.getEnemy();
                    if (enemy != null) {
                        AbstractCommand command = enemy.getCommand();
                        if (command != null) {
                            BehaviorState state = command.execute();
                            enemy.getBehavior().changeState(state);
                        }
                        calcNewCommand(enemy);
                    }
                }));
    }

    /**
     * Creates enemies, related to respawn points.
     * Respawn point canProcessObject able to recreate an enemy if there no live enemy in the respawn point.
     */
    public void respawn() {
        Player player = gameContext.getPlayer();

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
     * Calculates and sets new command to enemy.
     *
     * @param enemy enemy whose command should be calculated
     */
    private void calcNewCommand(AbstractEnemy enemy) {
        boolean isNeedToAttack = false; // todo: check range and if already attacking
        boolean isWaitingEnough = isWaitingEnough(enemy);
        boolean isEndOfMove = isEndOfMove(enemy);

        AbstractCommand command = enemy.getCommand();
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
                command = commandFactory.createEnemyMapGoCommand(enemy, newX, newY);
            }
        } else if (isEndOfMove) {
            command = commandFactory.createEnemyWaitCommand(ENEMY_DEFAULT_WAIT_TIME);
        }
        enemy.setCommand(command);
    }

    private boolean isWaitingEnough(AbstractEnemy enemy) {
        if (enemy.getCommand() instanceof EnemyWaitCommand) {
            EnemyWaitCommand command = (EnemyWaitCommand) enemy.getCommand();
            return command.getWaitTime() <= 0;
        }
        return false;
    }

    private boolean isEndOfMove(AbstractEnemy enemy) {
        if (enemy.getCommand() instanceof EnemyMapGoCommand) {
            EnemyMapGoCommand command = (EnemyMapGoCommand) enemy.getCommand();
            boolean isArrived = command.getX() == enemy.getX() && command.getY() == enemy.getY();
            boolean isCantMove = isCanMove(enemy);
            return isArrived || !isCantMove;
        }
        return false;
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
        Player player = gameContext.getPlayer();
        return isCoordinatesInRange(player.getX(), player.getY(), enemy.getX(), enemy.getY(), enemy.getAgrRange());
    }


    private boolean isCanMove(AbstractEnemy enemy) {
        List<AbstractEvent> events = eventContext.getEvents(enemy.getUniqueName());
        return !events.stream()
                .anyMatch(e -> e instanceof MapGoImpossibleEvent);
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
