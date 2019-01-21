package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Utility class for coordinates
 */
@Data
@Accessors(chain = true)
public class Coordinate {

    private String id;
    private int x;
    private int y;
}