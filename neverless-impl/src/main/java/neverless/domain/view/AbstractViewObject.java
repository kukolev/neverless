package neverless.domain.view;

import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class AbstractViewObject {

    private int x;
    private int y;

    public abstract void draw(GraphicsContext gc);
}
