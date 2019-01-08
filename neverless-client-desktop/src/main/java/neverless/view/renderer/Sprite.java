package neverless.view.renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import lombok.Data;
import lombok.experimental.Accessors;
import neverless.MapObjectMetaType;
import neverless.dto.CoordinateDto;
import neverless.PlatformShape;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Sprite {

    private Image image;

    private int x;
    private int y;

    private PlatformShape platformShape;
    private List<CoordinateDto> platformCoordinates = new ArrayList<>();
    private int platformShapeWidth;
    private int platformShapeHeight;
    private int platformCenterX;
    private int platformCenterY;
    private MapObjectMetaType metaType;
    private String id;



    /**
     * Constructor for Sprite.
     *
     * @param image graphical resource associated with sprite.
     */
    public Sprite(Image image) {
        this.image = image;
    }

    /**
     * Draws sprite on graphical context at x and y platformCoordinates.
     *
     * @param gc graphical context where we should draw the sprite.
     */
    public void draw(GraphicsContext gc) {
        drawPlatform(gc);
        gc.drawImage(image, x, y);
    }

    private void drawPlatform(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        switch (platformShape) {
            case ELLIPSE: {
                int dx = (int) (image.getWidth() - platformShapeWidth);
                int centerX = x - (dx / 2);

                int dy = (int) (image.getHeight() - platformShapeHeight / 2);
                int centerY = y + dy;
                gc.strokeArc(
                        centerX,
                        centerY,
                        platformShapeWidth,
                        platformShapeHeight, 0, 360, ArcType.OPEN);
            } break;

            case RECTANGLE: {

            } break;

            case CUSTOM: {
                for (int i = 0; i < platformCoordinates.size(); i++) {
                    CoordinateDto point1 = platformCoordinates.get(i);
                    CoordinateDto point2;
                    if (i == platformCoordinates.size() - 1) {
                        point2 = platformCoordinates.get(0);
                    } else {
                        point2 = platformCoordinates.get(i + 1);
                    }
                    gc.strokeLine(x + point1.getX(), y + point1.getY(), x + point2.getX(), y + point2.getY());
                }
            } break;
        }
    }
}