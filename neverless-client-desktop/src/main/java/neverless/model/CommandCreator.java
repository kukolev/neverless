package neverless.model;

import lombok.Data;
import neverless.Direction;
import neverless.MapObjectMetaType;
import neverless.dto.MapObjectDto;
import neverless.dto.PlayerDto;
import neverless.dto.player.GameStateDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static neverless.Constants.LOCAL_MAP_CELL_STEPS;
import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;
import static neverless.Direction.DOWN;
import static neverless.Direction.LEFT;
import static neverless.Direction.RIGHT;
import static neverless.Direction.UP;
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
     * Utility class for platformCoordinates.
     */
    @Data
    private class Coordinate {
        int x;
        int y;
    }

    /**
     * Evaluates a command that should be performed by clicking in some platformCoordinates.
     * Puts the command in queue.
     *
     * @param screenX horizontal cell index
     * @param screenY vertical cell index
     */
    public void click(int screenX, int screenY) {
        // todo: remove stub and implement real code.
        MapObjectMetaType metaType = getMetaTypeAtScreenPosition(screenX, screenY);
        System.out.println(metaType);

        GameStateDto gameState = this.frameExchanger.getFrame().getGameState();
        PlayerDto player = gameState.getPlayerScreenDataDto().getPlayerDto();

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
                MapObjectDto object = getObjectAtScreenPosition(screenX, screenY);
                if (object != null) {
                    cmdFightingAttack(object.getUniqueName());
                }
            }
            break;
        }
    }

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

    /**
     * Returns meta-type of object on local map with some screenX and screenY platformCoordinates.
     *
     * @param screenX horizontal screen coordinate.
     * @param screenY vertical screen coordinate.
     */
    private MapObjectMetaType getMetaTypeAtScreenPosition(int screenX, int screenY) {

        MapObjectDto object = getObjectAtScreenPosition(screenX, screenY);
        if (object != null) {
            return object.getMetaType();
        } else {
            return MapObjectMetaType.TERRAIN;
        }
    }

    private MapObjectDto getObjectAtScreenPosition(int screenX, int screenY) {
        Coordinate coordinate = getObjectCoordinates(screenX, screenY);
        List<MapObjectDto> objects = this.frameExchanger.getFrame().getGameState().getLocalMapScreenData().getObjects();
        return objects.stream()
                .filter(o -> isObjectAtPosition(o, coordinate.getX(), coordinate.getY()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns true if object is covering some coordinate.
     *
     * @param object the object for position analysis.
     * @param x      horizontal coordinate.
     * @param y      vertical coordinate.
     */
    private boolean isObjectAtPosition(MapObjectDto object, int x, int y) {
        boolean b = (object.getX() <= x) &&
                (object.getY() <= y) &&
                (x < (object.getX() + object.getPlatformWidth())) &&
                (y < (object.getY() + object.getPlatformHeight()));
        if (b) {
            System.out.println(object.getUniqueName());
        }
        return b;
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

    /**
     * Returns game platformCoordinates for some couple of screen platformCoordinates.
     *
     * @param screenX horizontal screen coordinate.
     * @param screenY vertical screen coordinate.
     */
    private Coordinate getObjectCoordinates(int screenX, int screenY) {
        int cellX = convertToCellPosition(screenX);
        int cellY = convertToCellPosition(screenY);

        // Create copy of reference to avoid concurrency issues in further code.
        GameStateDto gameState = this.frameExchanger.getFrame().getGameState();
        PlayerDto player = gameState.getPlayerScreenDataDto().getPlayerDto();
        int dx = player.getX() - (CANVAS_WIDTH / 2);
        int dy = player.getY() - (CANVAS_HEIGHT / 2);

        Coordinate coordinate = new Coordinate();
        coordinate.setX(cellX + dx);
        coordinate.setY(cellY + dy);

        return coordinate;
    }
}