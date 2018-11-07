package neverless.service.screendata;

import neverless.domain.GameObjectId;
import neverless.domain.mapobject.Player;
import neverless.domain.mapobject.monster.AbstractEnemy;
import neverless.domain.mapobject.respawn.AbstractRespawnPoint;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.repository.RespawnPointRepository;
import neverless.service.core.RequestContext;
import neverless.util.Coordinate;
import neverless.util.CoordinateUtils;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.lang.Math.abs;
import static neverless.util.CoordinateUtils.isCoordinatesInRange;

@Service
@Transactional
public class EnemyService extends AbstractService {

    @Autowired
    private RespawnPointRepository respawnPointRepository;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private MapObjectsRepository mapObjectsRepository;
    @Autowired
    private LocalMapService localMapService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SessionUtil sessionUtil;

    /**
     * Initiates a moving/attacking for all enemies.
     */
    public void move() {
        respawnPointRepository.findAllObjects()
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

        Player player = playerRepository.get();
        boolean chaseOrAttack = player.getLocation().equals(enemy.getLocation())
                    && isCoordinatesInRange(player.getX(), player.getY(), enemy.getX(), enemy.getY(), enemy.getAgrRange());

        if (!chaseOrAttack) {

            // 1. create list of ways where enemy can go
            List<Coordinate> coordinates = new ArrayList<>();
            addWalkDirection(enemy.getX() + 1, enemy.getY(), enemy, coordinates);
            addWalkDirection(enemy.getX() - 1, enemy.getY(), enemy, coordinates);
            addWalkDirection(enemy.getX(), enemy.getY() + 1, enemy, coordinates);
            addWalkDirection(enemy.getX(), enemy.getY() - 1, enemy, coordinates);

            if (coordinates.size() > 0) {
                // 2. choose random direction
                Random random = new Random(System.currentTimeMillis());
                int crdInd = random.nextInt(coordinates.size());
                Coordinate newCoordinate = coordinates.get(crdInd);

                // 3. set new coordinates
                enemy.setX(newCoordinate.getX());
                enemy.setY(newCoordinate.getY());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates possibility of walk to new coordinates and add the coordinates in list;
     *
     * @param newX      new X coordinate for enemy
     * @param newY      new Y coordinate for enemy
     * @param enemy     enemy for which new coordinates are calculated
     * @param coordinates    list of new coordinates
     */
    private void addWalkDirection(int newX, int newY, AbstractEnemy enemy, List<Coordinate> coordinates) {
        boolean isPassable = (localMapService.isPassable(newX, newY, enemy.getLocation()));
        boolean isNear =
                (newX <= enemy.getBornX() + enemy.getAreaX())
                && (newY <= enemy.getBornY() + enemy.getAreaY())
                && (newX >= enemy.getBornX() - enemy.getAreaX())
                && (newY >= enemy.getBornY() - enemy.getAreaY());

        if (isNear && isPassable) {
            coordinates.add(new Coordinate()
            .setX(newX)
            .setY(newY));
        }
    }

    /**
     * Changing position in aggressive mode.
     * Enemy should chase the player.
     *
     * @param enemy enemy that should chasing the player.
     */
    private boolean chase(AbstractEnemy enemy) {
        Coordinate coordinate = getNextCoordinatesForLos(enemy);
        if (localMapService.isPassable(coordinate.getX(), coordinate.getY(), enemy.getLocation())) {
            enemy
                    .setX(coordinate.getX())
                    .setY(coordinate.getY());
        }
        return true;
    }

    private Coordinate getNextCoordinatesForLos(AbstractEnemy enemy) {
        Player player = playerRepository.get();
        int playerX = player.getX();
        int playerY = player.getY();
        int enemyX = enemy.getX();
        int enemyY = enemy.getY();

        // return getNextCoordinatesForLos point if there are no impases on LoS
        return CoordinateUtils.getNextCoordinatesForLos(playerX, playerY, enemyX, enemyY);
    }

    /**
     * Attacks the player
     *
     * @param enemy enemy that should attack the player.
     */
    private void attack(AbstractEnemy enemy) {

    }

    public void respawn() {

        List<AbstractRespawnPoint> points = respawnPointRepository.findAllObjects();
        points.forEach(p -> {

            // Guarantee that new enemy would not be created
            // until current is alive
            if (p.getEnemy() != null) {
                p.setLastTurnInLife(requestContext.getTurnNumber());
            }
            int delta = requestContext.getTurnNumber() - p.getLastTurnInLife();

            // Calculates if new enemy should be respawn
            if (delta >= p.getRespawnPeriod()) {
                p.setLastTurnInLife(requestContext.getTurnNumber());
                if (p.getEnemy() == null) {
                    // todo: raise an event ??
                    AbstractEnemy enemy = p.respawnEnemy();
                    enemy.setId(sessionUtil.createId());
                    p.setEnemy(enemy);
                }
            }
        });
    }
}