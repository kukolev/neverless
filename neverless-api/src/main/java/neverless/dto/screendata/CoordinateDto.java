package neverless.dto.screendata;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Utility class for custom Coordinates
 */
@Data
@Accessors(chain = true)
public class CoordinateDto {
    private int x;
    private int y;
}