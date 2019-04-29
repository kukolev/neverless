package neverless.view.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class Resource {

    public Resource(String file, int left, int top, int width, int height) {
        this.file = file;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    private String file;
    private int left;
    private int top;
    private int width;
    private int height;
}
