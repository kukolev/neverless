package neverless.domain.event;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MapGoImpossibleEvent extends AbstractEvent {

    private String id;
}
