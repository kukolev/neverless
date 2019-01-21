package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.command.AbstractCommand;

@Data
@Accessors(chain = true)
public class Behavior {

    private AbstractCommand command;
    private BehaviorStage behaviorStage;
}