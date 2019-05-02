package neverless.core;

import javafx.concurrent.Task;
import neverless.domain.view.DestinationMarkerData;
import neverless.context.ViewContext;
import neverless.service.model.EventHandler;
import neverless.service.model.command.AbstractCommand;
import neverless.service.FrameExchanger;
import neverless.domain.view.Frame;
import neverless.service.view.Renderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class GameLoop extends Task {

    @Autowired
    private CommandResolver resolver;
    @Autowired
    private Renderer renderer;
    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private EventHandler eventHandler;

    private volatile boolean isWorking = true;
    private volatile boolean isPause = false;

    private Queue<AbstractCommand> queue = new ConcurrentLinkedQueue<>();
    private ViewContext viewContext = new ViewContext();

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
     * Sets information of mouse pointer coordinates.
     *
     * @param screenX horizontal coordinate.
     * @param screenY vertical coordinate.
     */
    public void setScreenPoint(int screenX, int screenY) {
        viewContext.setScreenPoint(screenX, screenY);
    }

    /**
     * Sets destination marker.
     *
     * @param markerX game horizontal coordinate for destination marker.
     * @param markerY game vertical coordinate for destination marker.
     */
    public void setDestinationMarker(int markerX, int markerY) {
        viewContext.setMarker(new DestinationMarkerData()
                .setX(markerX)
                .setY(markerY));
    }

    @Override
    public Object call() {
        try {
            while (isWorking) {
                long startTime = System.nanoTime();

                // 1. Get command from queue
                AbstractCommand command = null;
                if (!isPause && queue.size() != 0) {
                    command = queue.poll();
                }

                // 2. Send command to engine + receive data from last one
                resolveCommand(command);

                long finTime = System.nanoTime();
                long dt = 10 - ((finTime - startTime) / 1000_000);

                if (dt > 0) {
                    try {
                        Thread.sleep(dt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Throwable e) {
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
     * Toggles active setPause.
     */
    public void pause() {
        isPause = !isPause;
        renderer.setPause(isPause);
    }

    /**
     * Resolves command and updates current game state.
     * Sends actual game state to renderer.
     *
     * @param command command that should be resolved.
     */
    private void resolveCommand(AbstractCommand command) {
        if (!isPause) {
            // send command to backend and get response
            resolver.resolve(command);

            // handle events
            eventHandler.handleEvents(viewContext);
        }

        // render frame for response
        Frame frame = renderer.calcFrame(viewContext);

        // store frame and send acknowledge to Drawer
        frameExchanger.setFrame(frame);
        updateMessage(UUID.randomUUID().toString());
    }
}