package neverless.model;

import lombok.Data;
import neverless.dto.MapObjectMetaType;
import neverless.dto.command.Direction;
import neverless.dto.screendata.MapObjectDto;
import neverless.dto.screendata.PlayerDto;
import neverless.dto.screendata.player.GameStateDto;
import neverless.model.command.AbstractCommand;
import neverless.model.command.MapGoDownCommand;
import neverless.model.command.MapGoLeftCommand;
import neverless.model.command.MapGoRightCommand;
import neverless.model.command.MapGoUpCommand;
import neverless.model.command.StartNewGameCommand;
import neverless.model.command.WaitCommand;
import neverless.util.FrameExchanger;
import neverless.util.ResponseExchanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Math.floor;
import static neverless.util.Constants.CELL_HOR_CENTER;
import static neverless.util.Constants.CELL_VERT_CENTER;
import static neverless.util.Constants.CELL_WIDTH;

@Component
public class Model extends Thread {

    @Autowired
    private ResolverRouterService resolver;
    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private ResponseExchanger dtoExchanger;

    private volatile boolean isWorking = true;
    private volatile GameStateDto gameState;
    private Queue<AbstractCommand> queue = new ConcurrentLinkedQueue<>();

    /** Utility class for coordinates. */
    @Data
    private class Coordinate {
        int x;
        int y;
    }

    /**
     * Clears command queue and adds list of commands.
     *
     * @param commandList   list of command added to queue.
     */
    public void putCommandList(List<AbstractCommand> commandList) {
        queue.clear();
        queue.addAll(commandList);
    }

    /**
     * Clears command queue and adds one command.
     *
     * @param command   command added to queue.
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

        switch (metaType) {
            case TERRAIN: {
                int cellX = convertToCellPosition(screenX);
                int cellY = convertToCellPosition(screenY);

                Direction direction = calcDirection(cellX, cellY);
                switch (direction) {
                    case DOWN: cmdMapGoDown(); break;
                    case UP: cmdMapGoUp(); break;
                    case LEFT: cmdMapGoLeft(); break;
                    case RIGHT: cmdMapGoRight(); break;
                }
                System.out.println(direction);
            }
        }
    }

    public void cmdStartNewGame() {
        putCommand(new StartNewGameCommand());
        this.start();
    }

    public void cmdMapGoDown() {
        putCommand(new MapGoDownCommand());
    }

    public void cmdMapGoUp() {
        putCommand(new MapGoUpCommand());
    }

    public void cmdMapGoLeft() {
        putCommand(new MapGoLeftCommand());
    }

    public void cmdMapGoRight() {
        putCommand(new MapGoRightCommand());
    }

    @Override
    public void run() {
        while (isWorking) {
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

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
     *
     * @param command   command that should be resolved.
     */
    private void resolveCommand(AbstractCommand command) {
        gameState = resolver.resolve(command);

        try {
            dtoExchanger.exchange(gameState);
        } catch (InterruptedException e) {
            this.interrupt();
            e.printStackTrace();
        }
    }

    /**
     * Returns metatype of object on local map with some screenX and screenY coordinates.
     *
     * @param screenX horizontal screen coordinate.
     * @param screenY vertical screen coordinate.
     */
    private MapObjectMetaType getMetaTypeAtScreenPosition(int screenX, int screenY) {
        Coordinate coordinate = getObjectCoords(screenX, screenY);
        List<MapObjectDto> objects = gameState.getLocalMapScreenData().getObjects();
        return objects.stream()
                .filter(o -> isObjectAtPosition(o, coordinate.getX(), coordinate.getY()))
                .findFirst()
                .map(MapObjectDto::getMetaType)
                .orElse(MapObjectMetaType.TERRAIN);
    }

    /**
     * Returns game coordinates for some couple of screen coordinates.
     *
     * @param screenX horizontal screen coordinate.
     * @param screenY vertical screen coordinate.
     */
    private Coordinate getObjectCoords(int screenX, int screenY) {
        int cellX = convertToCellPosition(screenX);
        int cellY = convertToCellPosition(screenY);

        // Create copy of reference to avoid concurrency issues in further code.
        GameStateDto gameState = this.gameState;
        PlayerDto player = gameState.getPlayerScreenDataDto().getPlayerDto();
        int dx = player.getX() - CELL_HOR_CENTER;
        int dy = player.getY() - CELL_VERT_CENTER ;

        Coordinate coordinate = new Coordinate();
        coordinate.setX(cellX + dx);
        coordinate.setY(cellY + dy);

        return coordinate;
    }

    /**
     * Returns true if object is covering some coordinate.
     *
     * @param object    the object for position analysis.
     * @param x         horizontal coordinate.
     * @param y         vertical coordinate.
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
        return (int) (floor((double) coordinate / (double) CELL_WIDTH) + 1);
    }

    /**
     * Calculates and returns direction for vector between center of screen and some x and y coordinates
     *
     * @param x horizontal coordinate.
     * @param y vertical coordinate.
     */
    private Direction calcDirection(int x, int y) {
        int dx = x - CELL_HOR_CENTER;
        int dy = y - CELL_VERT_CENTER;

        double tg;
        if (dx != 0) {
            tg = (double) dy / dx;
        } else {
            tg = Integer.MAX_VALUE;
        }

        if (tg >= -1 && tg < 1) {
            if (dx > 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else {
            if (dy > 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }
    }
}