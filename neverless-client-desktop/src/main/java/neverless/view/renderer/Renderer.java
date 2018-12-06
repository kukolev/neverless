package neverless.view.renderer;

import javafx.concurrent.Task;
import neverless.dto.screendata.MapObjectDto;
import neverless.dto.screendata.player.GameStateDto;
import neverless.util.FrameExchanger;
import neverless.util.ResponseExchanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static neverless.util.Constants.CELL_HEIGHT;
import static neverless.util.Constants.CELL_HOR_CENTER;
import static neverless.util.Constants.CELL_VERT_CENTER;
import static neverless.util.Constants.CELL_WIDTH;

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
            GameStateDto gameStateDto = dtoExchanger.exchange(null);
            Scene scene = calcScene(gameStateDto);

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
     * @param gameStateDto   response from backend that include game state in concrete time.
     */
    private Scene calcScene(GameStateDto gameStateDto) {
        Scene scene = new Scene();
        Frame frame = calcFrame(gameStateDto, 0);
        scene.getFrames().add(frame);
        return scene;
    }



    private Frame calcFrame(GameStateDto gameStateDto, int percent) {

        double playerX = gameStateDto.getPlayerScreenDataDto().getPlayerDto().getX();
        double playerY = gameStateDto.getPlayerScreenDataDto().getPlayerDto().getY();

        Frame frame = new Frame();
        List<MapObjectDto> objects = gameStateDto.getLocalMapScreenData().getObjects();

        for (MapObjectDto o: objects) {
            Sprite sprite = calcSprite(o.getSignature(), o.getX(), o.getY(), playerX, playerY);
            frame.getSprites().add(sprite);
        }

        return frame;
    }

    private Sprite calcSprite(String signature, double objectX, double objectY, double playerX, double playerY) {
        int centerX = CELL_WIDTH * (CELL_HOR_CENTER - 1);
        int centerY = CELL_HEIGHT * (CELL_VERT_CENTER - 1);

        int imgX = (int) (centerX - (playerX - objectX) * CELL_WIDTH);
        int imgY = (int) (centerY - (playerY - objectY) * CELL_HEIGHT);

        return new Sprite(signature)
                .setX(imgX)
                .setY(imgY);
    }
}
