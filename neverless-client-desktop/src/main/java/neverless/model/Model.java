package neverless.model;

import neverless.dto.screendata.player.ResponseDto;
import neverless.resource.GameControllerResourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class Model extends Thread {

    @Autowired
    private GameControllerResourceImpl resource;

    private final int COMMAND_QUEUE_CAPACITY = 1000;

    private Queue<Command> queue = new ConcurrentLinkedDeque<>();

    @PostConstruct
    public void init() {
        start();
    }

    public void putCommandList(List<Command> commandList) {
        queue.clear();
        queue.addAll(commandList);
    }

    public void putCommand(Command command) {
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
        List<Command> commands = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        int commandCount = random.nextInt(10);
        for (int i = 0; i < commandCount; i++) {
            commands.add(new Command());
        }
        putCommandList(commands);
        System.out.println("put " + commands.size());
    }

    public void cmdStartNewGame() {

    }

    @Override
    public void run() {
        while (true) {
            // 1. Get command from queue
            Command command = queue.poll();

            // 2. Send command to engine + receive data from last one
            if (command != null) {
                System.out.println("command! " + queue.size());
                //resolveCommand(command);
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 3. Create scene/frames, etc. for renderer
            // nope

            // 4. Send to render
            // nope

            // 5. Goto 1.
        }
    }

    private void resolveCommand(Command command) {
        ResponseDto responseDto = resource.cmdStartNewGame();
        System.out.println(responseDto);
    }
}