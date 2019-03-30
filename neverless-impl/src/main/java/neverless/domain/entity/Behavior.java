package neverless.domain.entity;

public class Behavior {
    private BehaviorState state = BehaviorState.IDLE;
    private int ticks;

    public void changeState(BehaviorState newState) {
        if (newState != state) {
            state = newState;
            ticks = 0;
        }
        ticks++;
    }

    public BehaviorState getState() {
        return state;
    }
}
