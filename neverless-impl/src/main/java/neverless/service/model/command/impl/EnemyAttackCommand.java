package neverless.service.model.command.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.context.EventContext;
import neverless.domain.model.entity.item.weapon.AbstractHandEquipment;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemy;
import neverless.service.model.command.AbstractCommand;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.service.model.util.CombatService;
import neverless.service.model.util.LocalMapService;


@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class EnemyAttackCommand extends AbstractCommand {

    private AbstractEnemy enemy;
    private Player player;
    private LocalMapService localMapService;
    private EventContext eventContext;
    private CombatService combatService;

    public EnemyAttackCommand(AbstractEnemy enemy, Player player, LocalMapService localMapService, EventContext eventContext, CombatService combatService) {
        this.enemy = enemy;
        this.player = player;
        this.localMapService = localMapService;
        this.eventContext = eventContext;
        this.combatService = combatService;
    }

    @Override
    public BehaviorState onExecute() {
        if (!combatService.isWeaponCouldAttack(enemy, player)) {
            localMapService.makeStep(enemy, player.getX(), player.getY());
            return BehaviorState.MOVE;
        } else {
            AbstractHandEquipment weapon = enemy.getInventory().getEquipment().getWeapon();
            if (checkTicks(weapon.getSpeed())) {
                combatService.performAttack(enemy, player);
            }
            return BehaviorState.ATTACK;
        }
    }

    @Override
    public boolean checkFinished() {
        return player.getHitPoints() <= 0;
    }
}
