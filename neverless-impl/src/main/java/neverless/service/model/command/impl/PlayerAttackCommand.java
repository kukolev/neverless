package neverless.service.model.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.item.weapon.AbstractHandEquipment;
import neverless.service.model.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemy;
import neverless.service.model.util.CombatService;
import neverless.service.model.util.LocalMapService;

@Data
@Accessors(chain = true)
public class PlayerAttackCommand extends AbstractCommand {

    private AbstractEnemy enemy;
    private Player player;
    private LocalMapService localMapService;
    private EventContext eventContext;
    private CombatService combatService;

    public PlayerAttackCommand(AbstractEnemy enemy, Player player, LocalMapService localMapService, EventContext eventContext, CombatService combatService) {
        this.enemy = enemy;
        this.player = player;
        this.localMapService = localMapService;
        this.eventContext = eventContext;
        this.combatService = combatService;
    }



    @Override
    public BehaviorState onExecute() {

        if (!combatService.isWeaponCouldAttack(player, enemy)) {
            localMapService.makeStep(player, enemy.getX(), enemy.getY());
            return BehaviorState.MOVE;
        } else {
            AbstractHandEquipment weapon = player.getInventory().getEquipment().getWeapon();
            if (checkTicks(weapon.getSpeed())) {
                combatService.performAttack(player, enemy);
            }
            if (enemy.getHitPoints() <= 0) {
                enemy.getRespawnPoint().setEnemy(null);
            }
            return BehaviorState.ATTACK;
        }
    }

    @Override
    public boolean checkFinished() {
        return enemy.getHitPoints() <= 0;
    }
}
