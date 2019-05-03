package neverless.service.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import neverless.core.LocalMapPane;
import neverless.service.FrameExchanger;
import neverless.domain.view.AreaHighlighted;
import neverless.domain.view.Frame;
import neverless.domain.view.DestinationMarkerEffect;
import neverless.domain.view.ProfileWidget;
import neverless.domain.view.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;

@Component
public class Drawer implements ChangeListener<String> {

    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private LocalMapPane localMapPane;

    /**
     * Receives message from renderer and invokes other methods for displaying information.
     *
     * @param observable observable (is not used).
     * @param oldValue   old value of message (is not used).
     * @param newValue   new value (is not used).
     */
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Frame frame = frameExchanger.getFrame();
        displayGameState(frame.getProfileWidget());
        displayLog(frame.getLog());
        displayBackground(frame.getBackground());
        displayLocalMap(frame.getSprites());
        displayHighLights(frame.getSprites(), frame.getHighLighted());
        displayMarker(frame.getMarker());
        displayHighLighedArea(frame.getAreaHighlighted());
    }

    /**
     * Draws background of the game scene.
     *
     * @param background special sprite for background.
     */
    private void displayBackground(Sprite background) {
        GraphicsContext gc = localMapPane.getCanvas().getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        background.draw(gc);
    }

    /**
     * Draws graphic scene on game screen in local map pane.
     *
     * @param sprites    list of sprites for drawing.
     */
    private void displayLocalMap(List<Sprite> sprites) {
        if (sprites.size() > 0) {
            GraphicsContext gc = localMapPane.getCanvas().getGraphicsContext2D();
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
        GraphicsContext gc = localMapPane.getCanvas().getGraphicsContext2D();
        sprites.stream()
                .filter(s -> ids.contains(s.getId()))
                .forEach(s -> s.drawHighLight(gc));
    }

    /**
     * Displays details from game state on panes.
     */
    private void displayGameState(ProfileWidget profileWidget) {
        profileWidget.draw(localMapPane.getStatsArea());
    }

    /**
     * Draws destination marker.
     *
     * @param markerEffect  destination marker effect.
     */
    private void displayMarker(DestinationMarkerEffect markerEffect) {
        if (markerEffect != null) {
            markerEffect.draw(localMapPane.getCanvas().getGraphicsContext2D());
        }
    }

    /**
     * Draws high-lighted area.
     *
     * @param areaHighlighted   area that should be drawn.
     */
    private void displayHighLighedArea(AreaHighlighted areaHighlighted) {
        if (areaHighlighted != null) {
            areaHighlighted.draw(localMapPane.getCanvas().getGraphicsContext2D());
        }
    }

    /**
     * Displays log in specified log area.
     *
     * @param log   list of log items.
     */
    private void displayLog(List<String> log) {
        log.forEach(s -> localMapPane.getInfoArea().appendText("\n" + s));
    }
}