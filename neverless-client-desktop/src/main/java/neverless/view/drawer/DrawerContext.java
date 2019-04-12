package neverless.view.drawer;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;


/** Data class contains all components where information about game state should be displayed. */
@Data
@Accessors(chain = true)
@Component
public class DrawerContext {

    private Canvas localMapCanvas;
    private TextArea infoArea;
}
