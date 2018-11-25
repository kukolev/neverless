package neverless.view.renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Sprite {

    private static final String IMAGE_LOC = "http://icons.iconarchive.com/icons/uiconstock/flat-halloween/128/Halloween-Bat-icon.png";
    private static final Image image = new Image(IMAGE_LOC, 32, 32, true, true);

    private double x;
    private double y;
    private String signature;

    /**
     * Constructor for Sprite.
     *
     * @param signature identifier for graphical resource associated with sprite.
     */
    public Sprite(String signature) {
        this.signature = signature;
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