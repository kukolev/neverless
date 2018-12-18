package neverless.view.renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Sprite {

    private Image image;

    private double x;
    private double y;

    /**
     * Constructor for Sprite.
     *
     * @param image graphical resource associated with sprite.
     */
    public Sprite(Image image) {
        this.image = image;
    }

    /**
     * Draws sprite on graphical context at x and y coordinates.
     *
     * @param gc    graphical context where we should draw the sprite.
     */
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }
}