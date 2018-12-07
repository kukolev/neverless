package neverless.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import neverless.dto.screendata.player.GameStateDto;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;

@Component
public class Drawer implements ChangeListener<String> {

    @Autowired
    private FrameExchanger frameExchanger;

    private GraphicsContext gc;

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        try {
            Frame frame = frameExchanger.exchange(null);

            if (frame.getGameState() != null) {
                System.out.println(frame.getGameState());
            }
            
            if (frame.getSprites().size() > 0) {
                gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
                frame.getSprites().forEach(s -> s.draw(gc));
            } else {
                // gc.drawImage(image, 32, 32);
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
