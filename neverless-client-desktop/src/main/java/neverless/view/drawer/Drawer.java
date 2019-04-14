package neverless.view.drawer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import neverless.util.FrameExchanger;
import neverless.view.domain.Frame;
import neverless.view.domain.DestinationMarkerEffect;
import neverless.view.domain.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Drawer implements ChangeListener<String> {

    @Autowired
    private FrameExchanger frameExchanger;

    private DrawerContext context;

    /**
     * Sets graphic context.
     *
     * @param context context.
     */
    public void setGraphicsContext(DrawerContext context) {
        this.context = context;
    }

    /**
     * Receives message from renderer and invokes other methods for displaying information.
     *
     * @param observable observable (is not used).
     * @param oldValue   old value of message (is not used).
     * @param newValue   new value (is not used).
     */
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Frame frame = frameExchanger.getFrame();
        //displayGameState(frame.getGameState());
        displayLog(frame.getLog());
        displayLocalMap(frame.getBackground(), frame.getSprites());
        displayHighLights(frame.getSprites(), frame.getHighLighted());
        displayMarker(frame.getMarker());

    }

    /**
     * Draws graphic scene on game screen in local map pane.
     *
     * @param background special sprite for background.
     * @param sprites    list of sprites for drawing.
     */
    private void displayLocalMap(Sprite background, List<Sprite> sprites) {
        if (sprites.size() > 0) {
            GraphicsContext gc = context.getLocalMapCanvas().getGraphicsContext2D();
            background.draw(gc);
            sprites.forEach(s -> s.draw(gc));
        }
    }

    /**
     * Draws high-light effects for all sprites.
     *
     * @param sprites list of all sprites.
     * @param ids     list of sprites ids.
     */
    private void displayHighLights(List<Sprite> sprites, List<String> ids) {
        GraphicsContext gc = context.getLocalMapCanvas().getGraphicsContext2D();
        sprites.stream()
                .filter(s -> ids.contains(s.getId()))
                .forEach(s -> s.drawHighLight(gc));
        ids.forEach(System.out::println);
    }

    /**
     * Displays details from game state on panes.
     */
    private void displayGameState() {
        // todo: implement it!
    }

    /**
     * Draws destination marker.
     *
     * @param markerEffect  destination marker effect.
     */
    private void displayMarker(DestinationMarkerEffect markerEffect) {
        if (markerEffect != null) {
            markerEffect.draw(context.getLocalMapCanvas().getGraphicsContext2D());
        }
    }

    /**
     * Displays log in specified log area.
     *
     * @param log   list of log items.
     */
    private void displayLog(List<String> log) {
        log.forEach(s -> {
            context.getInfoArea().appendText("\n" + s);
        });
    }
}