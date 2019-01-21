package neverless.model;

import neverless.domain.entity.mapobject.Direction;
import neverless.MapObjectMetaType;
import neverless.domain.entity.mapobject.Player;
import neverless.command.AbstractCommand;
import neverless.command.player.FightingAttackCommand;
import neverless.command.MapGoCommand;
import neverless.command.StartNewGameCommand;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import neverless.view.renderer.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;
import static neverless.domain.entity.mapobject.Direction.DOWN;
import static neverless.domain.entity.mapobject.Direction.LEFT;
import static neverless.domain.entity.mapobject.Direction.RIGHT;
import static neverless.domain.entity.mapobject.Direction.UP;
import static neverless.MapObjectMetaType.TERRAIN;
import static neverless.util.Constants.CANVAS_CENTER_X;
import static neverless.util.Constants.CANVAS_CENTER_Y;

@Component
public class CommandCreator {

    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private Model model;

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
        System.out.println(metaType);
        Player player = frame.getGameState().getGame().getPlayer();

        switch (metaType) {
            case TERRAIN: {

                int dx = alignToGrid(screenX) - alignToGrid(CANVAS_CENTER_X);
                int dy = alignToGrid(screenY) - alignToGrid(CANVAS_CENTER_Y);

                int newGameX = player.getX() + dx;
                int newGameY = player.getY() + dy;

                MapGoCommand mapGoCommand = new MapGoCommand()
                        .setX(newGameX)
                        .setY(newGameY);
                model.putCommand(mapGoCommand);
            }
            break;

            case ENEMY: {
                    cmdFightingAttack(sprite.getId());
            }
            break;
        }
    }

    /**
     * Creates command for new game start.
     */
    public void cmdStartNewGame() {
        model.putCommand(new StartNewGameCommand());
        new Thread(model).start();
    }

    public void cmdMapGoDown() {
        model.putCommandList(createMapGoCommands(DOWN));
    }

    public void cmdMapGoUp() {
        model.putCommandList(createMapGoCommands(UP));
    }

    public void cmdMapGoLeft() {
        model.putCommandList(createMapGoCommands(LEFT));
    }

    public void cmdMapGoRight() {
        model.putCommandList(createMapGoCommands(RIGHT));
    }

    private List<AbstractCommand> createMapGoCommands(Direction direction) {
        List<AbstractCommand> commands = new ArrayList<>();
        // todo: implement
        return commands;
    }

    private void cmdFightingAttack(String enemyId) {
        model.putCommand(new FightingAttackCommand()
                .setEnemyId(enemyId));
    }

    private Sprite getSpriteAtScreenCoordinates(Frame frame, int screenX, int screenY) {
        List<Sprite> sprites = frame.getSprites().stream()
                .filter(s -> screenX >= s.getX() &&
                        screenX <= s.getX() + s.getImage().getWidth() &&
                        screenY >= s.getY() &&
                        screenY <= s.getY() + s.getImage().getHeight())
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