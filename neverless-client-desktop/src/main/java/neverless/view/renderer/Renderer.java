package neverless.view.renderer;

import javafx.scene.image.Image;
import neverless.PlatformShape;
import neverless.dto.MapObjectDto;
import neverless.dto.player.GameStateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;
import static neverless.view.drawer.DrawerUtils.calcRenderOrder;

@Component
public class Renderer {

    @Autowired
    private SpriteRepository spriteRepository;

    /**
     * Calculates and returns frames should be painted on game screen.
     *
     * @param gameStateDto response from backend that include game state in concrete time.
     */
    public Frame calcFrame(GameStateDto gameStateDto) {
        double playerX = gameStateDto.getPlayerScreenDataDto().getPlayerDto().getX();
        double playerY = gameStateDto.getPlayerScreenDataDto().getPlayerDto().getY();

        Frame frame = new Frame();

        String backSignature = gameStateDto.getLocalMapScreenData().getSignature();
        Sprite background = calcBackground(backSignature, playerX, playerY);
        frame.setBackground(background);

        List<MapObjectDto> objects = gameStateDto.getLocalMapScreenData().getObjects();
        List<Sprite> sprites = new ArrayList<>();
        for (MapObjectDto o : objects) {
            Sprite sprite = calcSprite(o, playerX, playerY);
            sprites.add(sprite);
        }
        frame.setSprites(calcRenderOrder(sprites));
        frame.setGameState(gameStateDto);
        return frame;
    }

    private Sprite calcBackground(String signature, double playerX, double playerY) {
        int centerX = CANVAS_WIDTH / 2;
        int centerY = CANVAS_HEIGHT / 2;

        Image image = spriteRepository.getImage(signature);

        int imgX = (int) (centerX - (playerX));
        int imgY = (int) (centerY - (playerY));

        return new Sprite(image)
                .setX(imgX)
                .setY(imgY)
                .setPlatformShape(PlatformShape.CUSTOM);
    }

    private Sprite calcSprite(MapObjectDto object, double playerX, double playerY) {
        int centerX = CANVAS_WIDTH / 2;
        int centerY = CANVAS_HEIGHT / 2;

        Image image = spriteRepository.getImage(object.getSignature());

        int imgX = (int) (centerX - (playerX - object.getX()));
        int imgY = (int) (centerY - (playerY - object.getY()));

        return new Sprite(image)
                .setX(imgX)
                .setY(imgY)
                .setPlatformCoordinates(object.getPlatformCoordinates())
                .setPlatformShape(object.getPlatformShape())
                .setPlatformShapeWidth(object.getPlatformWidth())
                .setPlatformShapeHeight(object.getPlatformHeight())
                .setPlatformCenterX(object.getPlatformCenterX())
                .setPlatformCenterY(object.getPlatformCenterY())
                .setMetaType(object.getMetaType())
                .setId(object.getUniqueName());
    }
}
