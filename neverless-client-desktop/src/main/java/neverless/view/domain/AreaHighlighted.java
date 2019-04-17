package neverless.view.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.AbstractArea;
import neverless.domain.entity.mapobject.Coordinate;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class AreaHighlighted extends AbstractViewObject {

    private AbstractArea abstractArea;
    private List<Coordinate> coordinates = new ArrayList<>();

    /**
     * Draws area.
     *
     * @param gc    graphic context.
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(10);

        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate point1 = coordinates.get(i);
            Coordinate point2;
            if (i == coordinates.size() - 1) {
                point2 = coordinates.get(0);
            } else {
                point2 = coordinates.get(i + 1);
            }
            gc.strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        }
    }
}
