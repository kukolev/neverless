package neverless.command.enemy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.command.CommandPayload;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class EnemyWaitPayload extends CommandPayload {

    private int waitTime;
}
