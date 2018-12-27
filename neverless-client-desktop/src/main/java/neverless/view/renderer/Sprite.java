package neverless.view.renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.experimental.Accessors;
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
    private List<Coordinate> customCoordinates = new ArrayList<>();
    private int platformShapeWidth;
    private int platformShapeHeight;

    /**
     * Constructor for Sprite.
     *
     * @param image graphical resource associated with sprite.
     */
    public Sprite(Image image) {
        this.image = image;
    }

    /**
     * Draws sprite on graphical context at x and y customCoordinates.
     *
     * @param gc    graphical context where we should draw the sprite.
     */
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }
}