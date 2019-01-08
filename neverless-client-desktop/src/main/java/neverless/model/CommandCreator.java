package neverless.model;

import neverless.Direction;
import neverless.MapObjectMetaType;
import neverless.dto.PlayerDto;
import neverless.model.command.AbstractCommand;
import neverless.model.command.FightingAttackCommand;
import neverless.model.command.MapGoDownCommand;
import neverless.model.command.MapGoDownLeftCommand;
import neverless.model.command.MapGoDownRightCommand;
import neverless.model.command.MapGoLeftCommand;
import neverless.model.command.MapGoRightCommand;
import neverless.model.command.MapGoUpCommand;
import neverless.model.command.MapGoUpLeftCommand;
import neverless.model.command.MapGoUpRightCommand;
import neverless.model.command.StartNewGameCommand;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import neverless.view.renderer.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static neverless.Constants.LOCAL_MAP_CELL_STEPS;
import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;
import static neverless.Direction.DOWN;
import static neverless.Direction.LEFT;
import static neverless.Direction.RIGHT;
import static neverless.Direction.UP;
import static neverless.MapObjectMetaType.TERRAIN;
import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;
import static neverless.util.CoordinateUtils.line;

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
        PlayerDto player = frame.getGameState().getPlayerScreenDataDto().getPlayerDto();

        int cellX = convertToCellPosition(screenX - player.getPlatformCenterX());
        int cellY = convertToCellPosition(screenY - player.getPlatformCenterY());

        switch (metaType) {
            case TERRAIN: {
                int centerX = convertToCellPosition(CANVAS_WIDTH / 2);
                int centerY = convertToCellPosition(CANVAS_HEIGHT / 2);

                List<Direction> directions = line(centerX, centerY, cellX, cellY);
                model.putCommandList(createMapGoCommands(directions));
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
        for (int i = 0; i < LOCAL_MAP_CELL_STEPS; i++) {
            switch (direction) {
                case DOWN:
                    commands.add(new MapGoDownCommand());
                    break;
                case UP:
                    commands.add(new MapGoUpCommand());
                    break;
                case LEFT:
                    commands.add(new MapGoLeftCommand());
                    break;
                case RIGHT:
                    commands.add(new MapGoRightCommand());
                    break;
            }
        }
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
     * Converts and returns cell number, converted from screen pixel coordinate
     *
     * @param coordinate coordinate of pixel on the screen.
     */
    private int convertToCellPosition(int coordinate) {
        return coordinate / LOCAL_MAP_STEP_LENGTH;
    }

    private List<AbstractCommand> createMapGoCommands(List<Direction> directions) {
        List<AbstractCommand> commands = new ArrayList<>();
        for (Direction direction : directions) {
            switch (direction) {
                case DOWN:
                    commands.add(new MapGoDownCommand());
                    break;
                case UP:
                    commands.add(new MapGoUpCommand());
                    break;
                case LEFT:
                    commands.add(new MapGoLeftCommand());
                    break;
                case RIGHT:
                    commands.add(new MapGoRightCommand());
                    break;
                case UP_LEFT:
                    commands.add(new MapGoUpLeftCommand());
                    break;
                case UP_RIGHT:
                    commands.add(new MapGoUpRightCommand());
                    break;
                case DOWN_LEFT:
                    commands.add(new MapGoDownLeftCommand());
                    break;
                case DOWN_RIGHT:
                    commands.add(new MapGoDownRightCommand());
                    break;
            }
        }
        return commands;
    }
}