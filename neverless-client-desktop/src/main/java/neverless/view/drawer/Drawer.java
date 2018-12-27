package neverless.view.drawer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import neverless.dto.screendata.PlayerDto;
import neverless.dto.screendata.player.GameStateDto;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import neverless.view.renderer.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;

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
            displayLocalMap(frame.getSprites());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws graphic scene on game screen in local map pane.
     *
     * @param sprites list of sprites for drawing.
     */
    private void displayLocalMap(List<Sprite> sprites) {
        if (sprites.size() > 0) {
            GraphicsContext gc = context.getLocalMapCanvas().getGraphicsContext2D();
            gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
            sprites.forEach(s -> {
                //drawPlatformShape(s);
                s.draw(gc);
            });
        }
    }

    /**
     * Draws platform shape under sprite.
     */
    private void drawPlatformShape(Sprite sprite) {
        switch (sprite.getPlatformShape()) {
            case ELLIPSE: {
                int dx = (int) (sprite.getImage().getWidth() - sprite.getPlatformShapeWidth());
                int centerX = sprite.getX() - (dx / 2);

                int dy = (int) (sprite.getImage().getHeight() - sprite.getPlatformShapeHeight() / 2);
                int centerY = sprite.getY() + dy;
                GraphicsContext gc = context.getLocalMapCanvas().getGraphicsContext2D();
                gc.fillArc(
                        centerX,
                        centerY,
                        sprite.getPlatformShapeWidth(),
                        sprite.getPlatformShapeHeight(), 0, 360, ArcType.OPEN);
                break;
            }
            case RECTANGLE: {
                break;
            }
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