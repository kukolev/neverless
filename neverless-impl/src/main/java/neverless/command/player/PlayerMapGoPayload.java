package neverless.command.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.command.CommandPayload;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PlayerMapGoPayload extends CommandPayload {

    private int x;
    private int y;
}
