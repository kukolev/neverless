package neverless.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MapGoCommand extends AbstractCommand{

    private int x;
    private int y;
}
