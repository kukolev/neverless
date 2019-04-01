package neverless.domain.entity;

import neverless.domain.entity.item.weapon.AbstractHandEquipment;

public class Behavior {
    private BehaviorState state = BehaviorState.IDLE;
    private AbstractHandEquipment weapon;
    private int ticks;

    public void changeState(BehaviorState newState) {
        if (newState != state) {
            state = newState;
            ticks = 0;
        }
    }

    public void changeWeapon(AbstractHandEquipment weapon) {
        if (this.weapon != weapon) {
            this.weapon = weapon;
            ticks = 0;
        }
    }

    public BehaviorState getState() {
        return state;
    }
}
