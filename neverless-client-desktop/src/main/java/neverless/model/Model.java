package neverless.model;

import neverless.dto.screendata.player.ResponseDto;
import neverless.model.command.AbstractCommand;
import neverless.model.command.StartNewGameCommand;
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
    private ResolverRouterService resolver;

    private Queue<StartNewGameCommand> queue = new ConcurrentLinkedDeque<>();

    @PostConstruct
    public void init() {
        start();
    }

    public void putCommandList(List<StartNewGameCommand> commandList) {
        queue.clear();
        queue.addAll(commandList);
    }

    public void putCommand(StartNewGameCommand command) {
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
        List<StartNewGameCommand> commands = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        int commandCount = random.nextInt(2) + 1;
        for (int i = 0; i < commandCount; i++) {
            commands.add(new StartNewGameCommand());
        }
        putCommandList(commands);
        System.out.println("put " + commands.size());
    }

    public void cmdStartNewGame() {
        putCommand(new StartNewGameCommand());
    }

    @Override
    public void run() {
        while (true) {
            // 1. Get command from queue
            AbstractCommand command = queue.poll();

            // 2. Send command to engine + receive data from last one
            if (command != null) {
                System.out.println("command! " + queue.size());
                resolveCommand(command);
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

    private void resolveCommand(AbstractCommand command) {
        ResponseDto responseDto = resolver.resolve(command);
        System.out.println(responseDto);
    }
}