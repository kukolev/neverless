package neverless.view.renderer;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Utility class for customCoordinates
 */
@Data
@Accessors(chain = true)
public class Coordinate {
    private int x;
    private int y;
}