package neverless.command.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.command.CommandPayload;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PlayerAttackPayload extends CommandPayload {

    private AbstractEnemy enemy;
}
