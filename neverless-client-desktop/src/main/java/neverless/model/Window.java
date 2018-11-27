package neverless.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Window {

    private int x;
    private int y;
    private int width = 32;
    private int height = 24;
}
