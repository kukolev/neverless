package neverless.service.model.command;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.behavior.BehaviorState;

@Data
@Accessors(chain = true)
public abstract class AbstractCommand {

    private int ticks;
    private BehaviorState state;

    /**
     * Sets behavior state and set ticks = 0.
     * Method performs if new state and current state are not equal.
     *
     * @param newState  new behavior state.
     */
    private void setState(BehaviorState newState) {
        if (newState != state) {
            state = newState;
            ticks = 0;
        }
    }

    /**
     * Executes command. Method performs common calculations and calls abstract onExecute method
     * which should be overridden in child classes.
     */
    public BehaviorState execute() {
        ticks++;
        BehaviorState newState = onExecute();
        setState(newState);
        return newState;
    }

    /**
     * Checks that a multiple of ticks has passed since the last state change.
     *
     * @param ticks     given ticks count for checking.
     */
    protected boolean checkTicks(int ticks) {
        return this.ticks % ticks == 0;
    }

    /**
     * Performs main logic of the command and returns current behavior state.
     */
    protected abstract BehaviorState onExecute();

    /**
     * Returns true if command aim is successfully achieved.
     */
    public abstract boolean checkFinished();
}
