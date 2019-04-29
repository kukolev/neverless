package neverless.view.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DestinationMarkerEffect extends AbstractViewObject {

    /**
     * Draws destination marker.
     *
     * @param gc    graphic context.
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        gc.strokeArc(
                getX(),
                getY(),
                10,
                10, 0, 360, ArcType.OPEN);
    }
}
