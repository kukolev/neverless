package neverless.model.command;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FightingAttackCommand extends AbstractCommand {

    private String enemyId;
}
