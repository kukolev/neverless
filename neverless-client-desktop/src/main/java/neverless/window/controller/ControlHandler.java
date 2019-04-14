package neverless.window.controller;

import neverless.context.GameContext;
import neverless.model.Model;
import neverless.service.command.factory.GameCommandFactory;
import neverless.service.command.factory.PlayerCommandFactory;
import neverless.MapObjectMetaType;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.util.FrameExchanger;
import neverless.view.domain.Frame;
import neverless.view.domain.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;
import static neverless.MapObjectMetaType.TERRAIN;
import static neverless.util.Constants.CANVAS_CENTER_X;
import static neverless.util.Constants.CANVAS_CENTER_Y;
import static neverless.util.SpriteUtils.getSpriteAtScreenCoordinates;

@Component
public class ControlHandler {

    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private Model model;
    @Autowired
    private PlayerCommandFactory commandFactory;
    @Autowired
    private GameCommandFactory gameCommandFactory;
    @Autowired
    private GameContext gameContext;

    /**
     * Evaluates a command that should be performed by clicking in some coordinates.
     * Puts the command in queue for further processing in Model.
     *
     * @param screenX horizontal cell index
     * @param screenY vertical cell index
     */
    public void click(int screenX, int screenY) {
        Frame frame = frameExchanger.getFrame();

        Sprite sprite = getSpriteAtScreenCoordinates(frame.getSprites(), screenX, screenY);
        MapObjectMetaType metaType = sprite != null ? sprite.getMetaType() : TERRAIN;
        Player player = gameContext.getPlayer();

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

    public void mouseMove(int screenX, int screenY) {
        model.setScreenPoint(screenX, screenY);
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

    /**
     * Converts and returns coordinate, aligned to coordinates grid, defined via LOCAL_MAP_STEP_LENGTH.
     *
     * @param coordinate aligned coordinate.
     */
    private int alignToGrid(int coordinate) {
        return LOCAL_MAP_STEP_LENGTH * (coordinate / LOCAL_MAP_STEP_LENGTH);
    }
}