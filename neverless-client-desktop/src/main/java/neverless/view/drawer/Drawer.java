package neverless.view.drawer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import neverless.dto.PlayerDto;
import neverless.dto.player.GameStateDto;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import neverless.view.renderer.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static neverless.view.drawer.DrawerUtils.calcRenderOrder;

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
        try {
            Frame frame = frameExchanger.exchange(null);
            displayGameState(frame.getGameState());
            displayLocalMap(frame.getBackground(), frame.getSprites());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws graphic scene on game screen in local map pane.
     *
     * @param background    special sprite for background.
     * @param sprites       list of sprites for drawing.
     */
    private void displayLocalMap(Sprite background, List<Sprite> sprites) {
        if (sprites.size() > 0) {
            GraphicsContext gc = context.getLocalMapCanvas().getGraphicsContext2D();
            background.draw(gc);

            List<Sprite> orderedSprites = calcRenderOrder(sprites);
            orderedSprites.forEach(s -> {
                s.draw(gc);
            });
        }
    }

    /**
     * Displays details from game state on panes.
     *
     * @param gameState game state for displaying.
     */
    private void displayGameState(GameStateDto gameState) {
        if (gameState != null) {
            PlayerDto playerDto = gameState.getPlayerScreenDataDto().getPlayerDto();
            String health = "Health points: " + playerDto.getHealthPoints().toString();
            context.getInfoArea().setText(health);
        }
    }
}