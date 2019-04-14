package neverless.model.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DestinationMarkerData {

    private int x;
    private int y;
}
