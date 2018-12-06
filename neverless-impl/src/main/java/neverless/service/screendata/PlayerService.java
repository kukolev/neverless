package neverless.service.screendata;

import neverless.context.EventContext;
import neverless.domain.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import neverless.dto.screendata.PlayerDto;
import neverless.dto.screendata.player.PlayerScreenDataDto;
import neverless.repository.EnemyRepository;
import neverless.repository.PlayerRepository;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private EnemyRepository enemyRepository;
    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private EventContext eventContext;

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
        AbstractEnemy enemy = enemyRepository.findById(sessionUtil.createId(enemyId)).get();
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
        Player player = playerRepository.get();
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
        enemyRepository.delete(enemy);
    }


    public PlayerScreenDataDto getScreenData() {
        PlayerDto playerDto = new PlayerDto();
        PlayerScreenDataDto screenDataDto = new PlayerScreenDataDto();

        Player player = playerRepository.get();
        playerDto
                .setHealthPoints(player.getHitPoints())
                .setLocation(player.getLocation())
                .setX(player.getX())
                .setY(player.getY());
        screenDataDto.setPlayerDto(playerDto);
        return screenDataDto;
    }
}