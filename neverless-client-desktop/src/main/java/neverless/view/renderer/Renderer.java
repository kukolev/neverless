package neverless.view.renderer;

import javafx.concurrent.Task;
import neverless.dto.screendata.player.ResponseDto;
import neverless.util.FrameExchanger;
import neverless.util.ResponseExchanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class Renderer extends Task {

    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private ResponseExchanger dtoExchanger;

    private AtomicBoolean isWorking = new AtomicBoolean(true);

    @Override
    protected Object call() throws Exception {

        while (isWorking.get()) {
            ResponseDto responseDto = dtoExchanger.exchange(null);
            Scene scene = calcScene(responseDto);

            for (Frame frame: scene.getFrames()) {
                updateMessage(UUID.randomUUID().toString());
                frameExchanger.exchange(frame);
                Thread.sleep(100);
            }
        }
        return null;
    }

    public void stop() {
        isWorking.set(false);
    }

    /**
     * Calculates and return list of frames (Scene) that should be painted on game screen.
     *
     * @param responseDto   response from backend that include game state in concrete time.
     */
    private Scene calcScene(ResponseDto responseDto) {
        System.out.println(responseDto);
        Scene scene = new Scene();
        scene.getFrames().add(new Frame());
        return scene;
    }
}
