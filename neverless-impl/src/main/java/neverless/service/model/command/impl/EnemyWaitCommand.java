package neverless.service.model.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.model.command.AbstractCommand;
import neverless.domain.model.entity.behavior.BehaviorState;

@Data
@Accessors(chain = true)
public class EnemyWaitCommand extends AbstractCommand {

    private int waitTime;

    @Override
    public BehaviorState onExecute() {
        waitTime--;
        return BehaviorState.IDLE;
    }

    @Override
    public boolean checkFinished() {
        return waitTime <= 0;
    }
}
