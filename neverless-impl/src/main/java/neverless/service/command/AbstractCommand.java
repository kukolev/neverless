package neverless.service.command;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.behavior.BehaviorState;

@Data
@Accessors(chain = true)
public abstract class AbstractCommand {
    public abstract BehaviorState execute();

    public abstract boolean checkFinished();
}
