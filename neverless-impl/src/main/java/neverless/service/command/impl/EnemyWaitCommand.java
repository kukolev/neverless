package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.domain.entity.BehaviorState;

@Data
@Accessors(chain = true)
public class EnemyWaitCommand extends AbstractCommand {

    private int waitTime;

    @Override
    public BehaviorState execute() {
        waitTime--;
        return BehaviorState.IDLE;
    }

    @Override
    public boolean checkFinished() {
        return waitTime <= 0;
    }
}
