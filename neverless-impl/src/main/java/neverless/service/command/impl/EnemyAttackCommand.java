package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.domain.entity.behavior.BehaviorState;

@Data
@Accessors(chain = true)
public class EnemyAttackCommand extends AbstractCommand {
    @Override
    public BehaviorState execute() {
        return null;
    }

    @Override
    public boolean checkFinished() {
        return false;
    }
}
