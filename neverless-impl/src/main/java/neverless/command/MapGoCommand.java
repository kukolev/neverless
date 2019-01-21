package neverless.command;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MapGoCommand extends AbstractCommand{

    private int x;
    private int y;
}
