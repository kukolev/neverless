package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.service.util.LocalMapService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static neverless.util.CoordinateUtils.calcDirection;
import static neverless.util.CoordinateUtils.calcNextStep;
import static neverless.util.CoordinateUtils.isCoordinatesInRange;

@Data
@Accessors(chain = true)
public class PlayerAttackCommand extends AbstractCommand {

    private AbstractEnemy enemy;
    private Player player;
    private LocalMapService localMapService;
    private EventContext eventContext;

    @Override
    public BehaviorState execute() {

        AbstractHandEquipment weapon = chooseBestWeapon(player, enemy);
        if (weapon == null) {

            Coordinate coordinate = calcNextStep(player.getX(), player.getY(), enemy.getX(), enemy.getY());
            if (localMapService.isPassable(player, coordinate.getX(), coordinate.getY())) {
                player.setX(coordinate.getX());
                player.setY(coordinate.getY());
                player.setDirection(calcDirection(player.getX(), player.getY(), enemy.getX(), enemy.getY()));
                eventContext.addMapGoEvent(player.getUniqueName(), player.getX(), player.getY(), enemy.getX(), enemy.getY());
            } else {
                eventContext.addMapGoImpossibleEvent(player.getUniqueName());
            }
            return BehaviorState.MOVE;

        } else {

            if (player.getBehavior().checkTime(weapon.getSpeed())) {
                player.getBehavior().replay();
                if (calcToHit(enemy)) {

                    // Player hits.
                    int damage = calcDamage(player, enemy);
                    enemy.decreaseHitPoints(damage);
                    if (enemy.getHitPoints() <= 0) {
                        killEnemy(enemy);
                        eventContext.addFightingEnemyKillEvent(enemy.getUniqueName());
                    } else {
                        eventContext.addFightingPlayerHitEvent(enemy.getUniqueName(), damage);
                    }
                } else {
                    // Player misses.
                    eventContext.addFightingPlayerMissEvent(enemy.getUniqueName());
                }
            }
            return BehaviorState.ATTACK;
        }
    }

    @Override
    public boolean checkFinished() {
        return enemy.getHitPoints() <= 0;
    }


    /**
     * Returns best weapon for attack.
     * Returns null if player hasn't weapon capable for attack.
     *
     * @param player main player
     * @param enemy  enemy attacked by the player.
     */
    private AbstractHandEquipment chooseBestWeapon(Player player, AbstractEnemy enemy) {
        AbstractHandEquipment rightWeapon = player.getInventory().getEquipment().getRightHand();
        AbstractHandEquipment leftWeapon = player.getInventory().getEquipment().getLeftHand();

        List<AbstractHandEquipment> weapons = new ArrayList<>();
        weapons.add(rightWeapon);
        weapons.add(leftWeapon);

        return weapons.stream()
                .filter(Objects::nonNull)
                .filter(w -> isCoordinatesInRange(player.getX(), player.getY(), enemy.getX(), enemy.getY(), w.getRange()))
                .max(Comparator.comparingInt(AbstractHandEquipment::getPower))
                .orElse(null);
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
     * @param player main player
     * @param enemy  enemy attacked by the player.
     */
    private int calcDamage(Player player, AbstractEnemy enemy) {
        AbstractHandEquipment rightWeapon = player.getInventory().getEquipment().getRightHand();
        AbstractHandEquipment leftWeapon = player.getInventory().getEquipment().getLeftHand();

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
