package neverless.view.renderer;

import javafx.scene.image.Image;
import lombok.Data;
import neverless.PlatformShape;
import neverless.context.EventContext;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.dto.event.EventsScreenDataDto;
import neverless.dto.GameStateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;
import static neverless.view.drawer.DrawerUtils.calcRenderOrder;

@Component
public class Renderer {

    @Autowired
    private SpriteRepository spriteRepository;
    @Autowired
    private EventContext eventContext;

    private Map<String, Phase> cache = new HashMap<>();

    @Data
    private class Phase {
        private String signature;
        private int phaseNumber = 1;
        private BehaviorState state = BehaviorState.IDLE;

        public String getFileName() {
            return signature + "_" + state + "_" + phaseNumber + ".png";
        }

        public void incPhaseNumber() {
            if (phaseNumber == 4) {
                phaseNumber = 1;
            } else phaseNumber++;
        }

        public void defPhaseNumber() {
            phaseNumber = 1;
        }
    }

    /**
     * Calculates and returns frames should be painted on game screen.
     *
     * @param gameStateDto response from backend that include game state in concrete time.
     */
    public Frame calcFrame(GameStateDto gameStateDto) {
        double playerX = gameStateDto.getGame().getPlayer().getX();
        double playerY = gameStateDto.getGame().getPlayer().getY();

        Frame frame = new Frame();

        String backSignature = gameStateDto.getGame().getPlayer().getLocation().getSignature();
        Sprite background = calcBackground(backSignature, playerX, playerY);
        frame.setBackground(background);

        List<AbstractMapObject> objects = gameStateDto.getGame().getPlayer().getLocation().getObjects();
        List<Sprite> sprites = new ArrayList<>();
        for (AbstractMapObject o : objects) {
            Sprite sprite = calcSprite(o, gameStateDto.getEventsScreenDataDto(), playerX, playerY);
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

    private Sprite calcSprite(AbstractMapObject object, EventsScreenDataDto events, double playerX, double playerY) {
        int centerX = CANVAS_WIDTH / 2;
        int centerY = CANVAS_HEIGHT / 2;

        String fileName = calcFileName(object, events);
        Image image = spriteRepository.getImage(fileName);

        int imgX = (int) (centerX - (playerX - object.getX()));
        int imgY = (int) (centerY - (playerY - object.getY()));

        return new Sprite(image)
                .setMapObject(object)
                .setX(imgX)
                .setY(imgY)
                .setPlatformCoordinates(object.getPlatformCoordinates())
                .setPlatformShape(object.getPlatformShape())
                .setPlatformShapeWidth(object.getPlatformWidth())
                .setPlatformShapeHeight(object.getPlatformHeight())
                .setHeight(object.getHeight())
                .setWidth(object.getWidth())
                .setMetaType(object.getMetaType())
                .setId(object.getUniqueName());
    }

    private String calcFileName(AbstractMapObject object, EventsScreenDataDto events) {
        Phase phase = cache.get(object.getUniqueName());
        if (phase == null) {
            phase = new Phase();
            phase.setSignature(object.getSignature());
            cache.put(object.getUniqueName(), phase);
        }

        BehaviorState newState = object.getBehavior().getState();
        if (phase.getState() != newState) {
            phase.defPhaseNumber();
            phase.setState(newState);
        } else {
            phase.incPhaseNumber();
        }

        return phase.getFileName();
    }

}
