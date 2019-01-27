package neverless.command;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Command {

    private CommandType commandType;
    private CommandPayload payload;
}
