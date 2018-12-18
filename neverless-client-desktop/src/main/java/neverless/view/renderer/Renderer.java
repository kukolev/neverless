package neverless.view.renderer;

import javafx.scene.image.Image;
import neverless.dto.screendata.MapObjectDto;
import neverless.dto.screendata.player.GameStateDto;
import neverless.util.FrameExchanger;
import neverless.util.ResponseExchanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;

@Component
public class Renderer {

    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private ResponseExchanger dtoExchanger;
    @Autowired
    private SpriteRepository spriteRepository;

    private AtomicBoolean isWorking = new AtomicBoolean(true);


    public void stop() {
        isWorking.set(false);
    }

    /**
     * Receives game state and stores it to queue for further processing.
     *
     * @param gameStateDto game state.
     */
    public Frame processGameState(GameStateDto gameStateDto) {
        Frame gameStateFrame = new Frame();
        gameStateFrame.setGameState(gameStateDto);

        return gameStateFrame;
    }

    public Scene processScene(GameStateDto gameStateDto) {
        long t = System.nanoTime();
        Scene scene = calcScene(gameStateDto);
        System.out.println("CalcScene = " + (System.nanoTime() - t));
        return scene;
    }

    /**
     * Calculates and return list of frames (Scene) that should be painted on game screen.
     *
     * @param gameStateDto response from backend that include game state in concrete time.
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
        int centerX = CANVAS_WIDTH / 2;
        int centerY = CANVAS_HEIGHT / 2;

        int imgX = (int) (centerX - (playerX - objectX));
        int imgY = (int) (centerY - (playerY - objectY));

        Image image = spriteRepository.getImage(signature);
        return new Sprite(image)
                .setX(imgX)
                .setY(imgY);
    }
}
