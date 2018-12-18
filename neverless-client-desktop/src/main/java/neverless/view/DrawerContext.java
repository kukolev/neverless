package neverless.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import lombok.Data;
import lombok.experimental.Accessors;


/** Data class contains all components where information about game state should be displayed. */
@Data
@Accessors(chain = true)
public class DrawerContext {

    private Canvas localMapCanvas;
    private TextArea infoArea;
}
