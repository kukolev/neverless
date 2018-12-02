package neverless.model;

import neverless.dto.MapObjectMetaType;
import neverless.dto.command.Direction;
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
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
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
    private final ReentrantLock gameStateLock = new ReentrantLock();

    private Queue<AbstractCommand> queue = new ConcurrentLinkedQueue<>();

    public void putCommandList(List<AbstractCommand> commandList) {
        queue.clear();
        queue.addAll(commandList);
    }

    public void putCommand(AbstractCommand command) {
        queue.clear();
        queue.add(command);
    }

    /**
     * Evaluates a command that should be performed by clicking in some coordinates.
     * Puts the command in queue.
     *
     * @param x horizontal cell index
     * @param y vertical cell index
     */
    public void click(int x, int y) {
        // todo: remove stub and implement real code.
        int cellX = convertToCellPosition(x);
        int cellY = convertToCellPosition(y);
        MapObjectMetaType metaType = getMetaTypeAtPosition(cellX, cellY);

        switch (metaType) {
            case TERRAIN: {
                Direction direction = calcDirection(cellX, cellY);
                switch (direction) {
                    case DOWN: putCommand(new MapGoDownCommand()); break;
                    case UP: putCommand(new MapGoUpCommand()); break;
                    case LEFT: putCommand(new MapGoLeftCommand()); break;
                    case RIGHT: putCommand(new MapGoRightCommand()); break;
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
        GameStateDto dto = resolver.resolve(command);
        gameStateLock.lock();
        gameState = dto;
        gameStateLock.unlock();

        try {
            dtoExchanger.exchange(gameState);
        } catch (InterruptedException e) {
            this.interrupt();
            e.printStackTrace();
        }
    }

    /**
     * Returns metatype of object on local map with some x and y coordinates.
     * @param x horizontal coordinate.
     * @param y vertical coordinate.
     */
    private MapObjectMetaType getMetaTypeAtPosition(int x, int y) {
        gameStateLock.lock();
        // todo: implement real calculation
        MapObjectMetaType metaType = MapObjectMetaType.TERRAIN;
        gameStateLock.unlock();
        return metaType;
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

        double tg = 0;
        double alfa = 0;
        if (dx != 0) {
            tg = (double) dy / dx;
            alfa = atan(tg) * (180 / PI);
        } else {
            alfa = 90;
        }

        if ((alfa > 0 && alfa < 45) || (alfa <= 0 && alfa >= -45)) {
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