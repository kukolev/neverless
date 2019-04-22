package neverless.service.util;

import neverless.context.EventContext;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.entity.mapobject.AbstractLiveObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static neverless.util.CoordinateUtils.isCoordinatesInRange;

@Service
public class CombatService {

    @Autowired
    private EventContext eventContext;

    /**
     * Returns true if attacked is able to attack with weapon.
     *
     * @param attacker      live object who attacks.
     * @param defender      live object who has been attacked.
     */
    public boolean isWeaponCouldAttack(AbstractLiveObject attacker, AbstractLiveObject defender) {
        AbstractHandEquipment weapon = attacker.getInventory().getEquipment().getWeapon();
        return isCoordinatesInRange(attacker.getX(), attacker.getY(), defender.getX(), defender.getY(), weapon.getRange());
    }

    /**
     * Calculates and returns damage, impacted by the attacker during attack to enemy.
     *
     * @param attacker      live object who attacks.
     * @param defender      live object who has been attacked.
     */
    public int calcDamage(AbstractLiveObject attacker, AbstractLiveObject defender) {
        AbstractHandEquipment weapon = attacker.getInventory().getEquipment().getWeapon();
        return weapon != null ? weapon.getPower() : 0;
    }
    /**
     * Calculates and returns true if attacker impacted defender.
     *
     * @param defender      live object who has been attacked.
     */
    public boolean calcToHit(AbstractLiveObject defender) {
        // todo: implement real calculation.
        Random random = new Random(System.currentTimeMillis());
        return random.nextBoolean();
    }

    /**
     * Performs attack from one live object towards some other.
     *
     * @param attacker      live object who attacks.
     * @param defender      live object who has been attacked.
     */
    public void performAttack(AbstractLiveObject attacker, AbstractLiveObject defender) {
            if (calcToHit(defender)) {

                // Player hits.
                int damage = calcDamage(attacker, defender);
                defender.decreaseHitPoints(damage);
                if (defender.getHitPoints() <= 0) {
                    kill(defender);
                    eventContext.addCombatKillEvent(defender.getUniqueName());
                } else {
                    eventContext.addCombatHitEvent(defender.getUniqueName(), damage);
                }
            } else {
                // Player misses.
                eventContext.addCombatMissEvent(defender.getUniqueName());
            }
//        }
    }

    /**
     * Kills a defender and performs needed actions.
     *
     * @param defender      live object who has been attacked.
     */
    public void kill(AbstractLiveObject defender) {
        defender.getLocation().getObjects().remove(defender);
    }
}
