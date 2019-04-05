package neverless.model;

import javafx.concurrent.Task;
import neverless.service.command.factory.PlayerCommandFactory;
import neverless.dto.GameStateDto;
import neverless.service.command.AbstractCommand;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import neverless.view.renderer.Renderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Model extends Task {

    @Autowired
    private ResolverRouterService resolver;
    @Autowired
    private Renderer renderer;
    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private PlayerCommandFactory playerCommandFactory;

    private volatile boolean isWorking = true;
    private Queue<AbstractCommand> queue = new ConcurrentLinkedQueue<>();

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
                    command = playerCommandFactory.createPlayerContinueCommand();
                }
                // 2. Send command to engine + receive data from last one
                if (command != null) {
                    resolveCommand(command);
                }

                long t2 = System.nanoTime();
                long dt = 10 - ((t2 - t) / 1000_000);

                if (dt > 1) {
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
     * Resolves command and updates current game state.
     * Sends actual game state to renderer.
     *
     * @param command command that should be resolved.
     */
    private void resolveCommand(AbstractCommand command) {
            // send command to backend and get response
            GameStateDto gameState = resolver.resolve(command);

            // render frame for response
            Frame frame = renderer.calcFrame(gameState);

            // store frame and send acknowledge to Drawer
            frameExchanger.setFrame(frame);
            updateMessage(UUID.randomUUID().toString());
    }
}