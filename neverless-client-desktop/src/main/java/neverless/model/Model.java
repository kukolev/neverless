package neverless.model;

import javafx.concurrent.Task;
import lombok.Data;
import neverless.MapObjectMetaType;
import neverless.Direction;
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
import neverless.model.command.WaitCommand;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import neverless.view.renderer.Renderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

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
public class Model extends Task {

    @Autowired
    private ResolverRouterService resolver;
    @Autowired
    private Renderer renderer;
    @Autowired
    private FrameExchanger frameExchanger;

    private volatile boolean isWorking = true;
    private volatile GameStateDto gameState;
    private Queue<AbstractCommand> queue = new ConcurrentLinkedQueue<>();

    /**
     * Utility class for platformCoordinates.
     */
    @Data
    private class Coordinate {
        int x;
        int y;
    }

    /**
     * Clears command queue and adds list of commands.
     *
     * @param commandList list of command added to queue.
     */
    public void putCommandList(List<AbstractCommand> commandList) {
        queue.clear();
        queue.addAll(commandList);
    }

    /**
     * Clears command queue and adds one command.
     *
     * @param command command added to queue.
     */
    public void putCommand(AbstractCommand command) {
        queue.clear();
        queue.add(command);
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

        GameStateDto gameState = this.gameState;
        PlayerDto player = gameState.getPlayerScreenDataDto().getPlayerDto();

        int cellX = convertToCellPosition(screenX - player.getPlatformCenterX());
        int cellY = convertToCellPosition(screenY - player.getPlatformCenterY());

        switch (metaType) {
            case TERRAIN: {
                int centerX = convertToCellPosition(CANVAS_WIDTH / 2);
                int centerY = convertToCellPosition(CANVAS_HEIGHT / 2);

                List<Direction> directions = line(centerX, centerY, cellX, cellY);
                putCommandList(createMapGoCommands(directions));
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

    private void cmdFightingAttack(String enemyId) {
        putCommand(new FightingAttackCommand()
                .setEnemyId(enemyId));
    }

    public void cmdStartNewGame() {
        putCommand(new StartNewGameCommand());
        new Thread(this).start();
    }

    public void cmdMapGoDown() {
        putCommandList(createMapGoCommands(DOWN));
    }

    public void cmdMapGoUp() {
        putCommandList(createMapGoCommands(UP));
    }

    public void cmdMapGoLeft() {
        putCommandList(createMapGoCommands(LEFT));
    }

    public void cmdMapGoRight() {
        putCommandList(createMapGoCommands(RIGHT));
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

    @Override
    public Object call() {
        try {
            while (isWorking) {
                long t = System.nanoTime();

                // 1. Get command from queue
                AbstractCommand command;
                if (queue.size() != 0) {
                    command = queue.poll();
                } else {
                    command = new WaitCommand();
                }
                // 2. Send command to engine + receive data from last one
                if (command != null) {
                    resolveCommand(command);
                }

                long t2 = System.nanoTime();
                long dt = 30 - ((t2 - t) / 1000_000);

                if (dt > 1) {
                    try {
                        Thread.sleep(dt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets isWorking = false, i.e. generates signal to stop the thread.
     * Loop in run method will breaks after next condition check.
     */
    public void exit() {
        isWorking = false;
    }

    /**
     * Resolves command and updates current game state.
     * Sends actual game state to renderer.
     *
     * @param command command that should be resolved.
     */
    private void resolveCommand(AbstractCommand command) {
        // send command to backend and get response
        long t = System.nanoTime();
        gameState = resolver.resolve(command);
        System.out.println("Resolve = " + (System.nanoTime() - t));

        // render frame for response
        Frame frame = renderer.calcFrame(gameState);

        // store frame and send acknowledge to Drawer
        frameExchanger.setFrame(frame);
        updateMessage(UUID.randomUUID().toString());
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
        List<MapObjectDto> objects = gameState.getLocalMapScreenData().getObjects();
        return objects.stream()
                .filter(o -> isObjectAtPosition(o, coordinate.getX(), coordinate.getY()))
                .findFirst()
                .orElse(null);
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
        GameStateDto gameState = this.gameState;
        PlayerDto player = gameState.getPlayerScreenDataDto().getPlayerDto();
        int dx = player.getX() - (CANVAS_WIDTH / 2);
        int dy = player.getY() - (CANVAS_HEIGHT / 2);

        Coordinate coordinate = new Coordinate();
        coordinate.setX(cellX + dx);
        coordinate.setY(cellY + dy);

        return coordinate;
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
}