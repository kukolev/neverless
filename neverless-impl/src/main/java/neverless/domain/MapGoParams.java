package neverless.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.command.Direction;

@Data
@Accessors(chain = true)
public class MapGoParams extends AbstractCommandParams {

    private Direction direction;
}
