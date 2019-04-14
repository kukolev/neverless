package neverless.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MapGoEvent extends AbstractEvent{

    private String id;
    private int x;
    private int y;
    private int targetX;
    private int targetY;
}
