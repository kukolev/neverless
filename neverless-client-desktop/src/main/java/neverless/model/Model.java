package neverless.model;

import javafx.concurrent.Task;
import lombok.Data;
import neverless.dto.MapObjectMetaType;
import neverless.dto.command.Direction;
import neverless.dto.screendata.MapObjectDto;
import neverless.dto.screendata.PlayerDto;
import neverless.dto.screendata.player.GameStateDto;
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
import neverless.view.renderer.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import static neverless.Constants.LOCAL_MAP_CELL_STEPS;
import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;
import static neverless.dto.command.Direction.DOWN;
import static neverless.dto.command.Direction.LEFT;
import static neverless.dto.command.Direction.RIGHT;
import static neverless.dto.command.Direction.UP;
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
     * Utility class for coordinates.
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
     * Evaluates a command that should be performed by clicking in some coordinates.
     * Puts the command in queue.
     *
     * @param screenX horizontal cell index
     * @param screenY vertical cell index
     */
    public void click(int screenX, int screenY) {
        // todo: remove stub and implement real code.
        MapObjectMetaType metaType = getMetaTypeAtScreenPosition(screenX, screenY);
        System.out.println(metaType);

        int cellX = convertToCellPosition(screenX);
        int cellY = convertToCellPosition(screenY);

        switch (metaType) {
            case TERRAIN: {
                GameStateDto curGameState = this.gameState;

                int centerX = convertToCellPosition(CANVAS_WIDTH / 2);
                int centerY = convertToCellPosition(CANVAS_HEIGHT / 2);

                int playerX = convertToCellPosition(curGameState.getPlayerScreenDataDto().getPlayerDto().getX());
                int playerY = convertToCellPosition(curGameState.getPlayerScreenDataDto().getPlayerDto().getY());
                List<Direction> directions = line(centerX, centerY, cellX, cellY);

                System.out.println(directions);

                putCommandList(createMapGoCommands(directions));

            } break;

            case ENEMY: {
                MapObjectDto object = getObjectAtScreenPosition(screenX, screenY);
                if (object != null) {
                    cmdFightingAttack(object.getUniqueName());
                }
            } break;
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
        for(int i = 0; i < LOCAL_MAP_CELL_STEPS; i++) {
            switch (direction) {
                case DOWN: commands.add(new MapGoDownCommand()); break;
                case UP: commands.add(new MapGoUpCommand()); break;
                case LEFT: commands.add(new MapGoLeftCommand()); break;
                case RIGHT: commands.add(new MapGoRightCommand()); break;
            }
        }
        return commands;
    }

    private List<AbstractCommand> createMapGoCommands(List<Direction> directions) {
        List<AbstractCommand> commands = new ArrayList<>();
        for(Direction direction: directions) {
            switch (direction) {
                case DOWN: commands.add(new MapGoDownCommand()); break;
                case UP: commands.add(new MapGoUpCommand()); break;
                case LEFT: commands.add(new MapGoLeftCommand()); break;
                case RIGHT: commands.add(new MapGoRightCommand()); break;
                case UP_LEFT: commands.add(new MapGoUpLeftCommand()); break;
                case UP_RIGHT: commands.add(new MapGoUpRightCommand()); break;
                case DOWN_LEFT: commands.add(new MapGoDownLeftCommand()); break;
                case DOWN_RIGHT: commands.add(new MapGoDownRightCommand()); break;
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
                AbstractCommand command = null;
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
        long t = System.nanoTime();
        gameState = resolver.resolve(command);
        System.out.println("Resolve = " + (System.nanoTime() - t));
        Frame gameStateFrame = renderer.processGameState(gameState);

        updateMessage(UUID.randomUUID().toString());

        try {
            frameExchanger.exchange(gameStateFrame);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Scene scene = renderer.processScene(gameState);

        for (Frame frame: scene.getFrames()) {
            updateMessage(UUID.randomUUID().toString());

            try {
                frameExchanger.exchange(frame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns meta-type of object on local map with some screenX and screenY coordinates.
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
     * Returns game coordinates for some couple of screen coordinates.
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
                (x < (object.getX() + object.getWidth())) &&
                (y < (object.getY() + object.getHeight()));
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
        return  coordinate / LOCAL_MAP_STEP_LENGTH;
    }

    /**
     * Calculates and returns direction for vector between center of screen and some x and y coordinates
     *
     * @param x horizontal coordinate.
     * @param y vertical coordinate.
     */
    private Direction calcDirection(int x, int y) {
        int dx = x - (CANVAS_WIDTH / 2);
        int dy = y - (CANVAS_HEIGHT / 2);

        double tg;
        if (dx != 0) {
            tg = (double) dy / dx;
        } else {
            tg = Integer.MAX_VALUE;
        }

        if (tg >= -1 && tg < 1) {
            if (dx > 0) {
                return RIGHT;
            } else {
                return LEFT;
            }
        } else {
            if (dy > 0) {
                return Direction.DOWN;
            } else {
                return UP;
            }
        }
    }
}