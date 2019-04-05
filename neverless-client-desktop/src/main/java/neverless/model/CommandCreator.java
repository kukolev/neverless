package neverless.model;

import neverless.service.command.factory.GameCommandFactory;
import neverless.service.command.factory.PlayerCommandFactory;
import neverless.MapObjectMetaType;
import neverless.domain.entity.mapobject.Player;
import neverless.service.command.AbstractCommand;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import neverless.view.renderer.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;
import static neverless.MapObjectMetaType.TERRAIN;
import static neverless.util.Constants.CANVAS_CENTER_X;
import static neverless.util.Constants.CANVAS_CENTER_Y;

@Component
public class CommandCreator {

    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private Model model;
    @Autowired
    private PlayerCommandFactory commandFactory;
    @Autowired
    private GameCommandFactory gameCommandFactory;

    /**
     * Evaluates a command that should be performed by clicking in some coordinates.
     * Puts the command in queue for further processing in Model.
     *
     * @param screenX horizontal cell index
     * @param screenY vertical cell index
     */
    public void click(int screenX, int screenY) {
        Frame frame = frameExchanger.getFrame();

        Sprite sprite = getSpriteAtScreenCoordinates(frame, screenX, screenY);
        MapObjectMetaType metaType = sprite != null ? sprite.getMetaType() : TERRAIN;
        Player player = frame.getGameState().getGame().getPlayer();

        switch (metaType) {
            case TERRAIN:
            case IMPASSIBLE_TERRAIN:    {

                int dx = alignToGrid(screenX) - alignToGrid(CANVAS_CENTER_X);
                int dy = alignToGrid(screenY) - alignToGrid(CANVAS_CENTER_Y);

                int newGameX = player.getX() + dx;
                int newGameY = player.getY() + dy;

                model.putCommand(commandFactory.createPlayerMapGoCommand(newGameX, newGameY));
            }
            break;

            case ENEMY: {
                if (sprite.getMapObject() instanceof AbstractEnemy) {
                    cmdFightingAttack((AbstractEnemy) sprite.getMapObject());
                }
            }
            break;
        }
    }

    /**
     * Creates command for new game start.
     */
    public void cmdStartNewGame() {
        model.putCommand(gameCommandFactory.createStartNewGameCommand());
        new Thread(model).start();
    }

    private void cmdFightingAttack(AbstractEnemy enemy) {
        model.putCommand(commandFactory.createPlayerAttackCommand(enemy));
    }

    private Sprite getSpriteAtScreenCoordinates(Frame frame, int screenX, int screenY) {
        List<Sprite> sprites = frame.getSprites().stream()
                .filter(s -> screenX >= s.getX() - s.getWidth() / 2 &&
                        screenX <= s.getX() + s.getWidth() / 2 &&
                        screenY >= s.getY() - s.getHeight() &&
                        screenY <= s.getY())
                .collect(Collectors.toList());
        if (sprites.size() > 0) {
            return sprites.get(sprites.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Converts and returns coordinate, aligned to coordinates grid, defined via LOCAL_MAP_STEP_LENGTH.
     *
     * @param coordinate aligned coordinate.
     */
    private int alignToGrid(int coordinate) {
        return LOCAL_MAP_STEP_LENGTH * (coordinate / LOCAL_MAP_STEP_LENGTH);
    }

}