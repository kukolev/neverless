package neverless.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FightingAttackCommand extends AbstractCommand {

    private String enemyId;
}
