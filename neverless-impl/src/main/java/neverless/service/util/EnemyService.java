package neverless.service.util;

import neverless.util.Coordinate;
import neverless.context.EventContext;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.context.GameContext;
import neverless.util.CoordinateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static neverless.Constants.DELTA_BETWEEN_NEAREST_PLATFORMS;
import static neverless.util.CoordinateUtils.isCurvesIntersected;

@Service
public class EnemyService {

    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private GameContext gameContext;


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
     * Returns true if enemy position is close to player.
     *
     * @param enemy checked enemy.
     */
    private boolean isPlayerNear(AbstractEnemy enemy) {
        Player player = gameContext.getPlayer();

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
        Player player = gameContext.getPlayer();
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
            Player player = gameContext.getPlayer();
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
}