package neverless.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.command.Command;
import neverless.domain.entity.mapobject.BehaviorStage;

import java.util.UUID;

@Data
@Accessors(chain = true)
public abstract class AbstractGameObject {

    private String uniqueName = UUID.randomUUID().toString();
    private Command command;
    private BehaviorStage behaviorStage;
}
