package neverless.service.view;

import javafx.scene.image.Image;
import lombok.Data;
import neverless.util.PlatformShape;
import neverless.context.EventContext;
import neverless.context.GameContext;
import neverless.domain.model.entity.Game;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.model.entity.mapobject.AbstractPhysicalObject;
import neverless.service.model.command.AbstractCommand;
import neverless.util.Coordinate;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.Profile;
import neverless.domain.view.DestinationMarkerData;
import neverless.util.CoordinateUtils;
import neverless.domain.view.AreaHighlighted;
import neverless.domain.view.DestinationMarkerEffect;
import neverless.domain.view.Frame;
import neverless.domain.view.Sprite;
import neverless.domain.view.Resource;
import neverless.game.ResourceKeeper;
import neverless.domain.view.ViewContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static neverless.util.Constants.ANIMATION_SLOW_FACTOR;
import static neverless.domain.model.entity.behavior.BehaviorState.IDLE;
import static neverless.util.Constants.CANVAS_CENTER_X;
import static neverless.util.Constants.CANVAS_CENTER_Y;
import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;
import static neverless.util.FrameUtils.getSpriteAtScreenCoordinates;
import static neverless.service.view.DrawerUtils.calcRenderOrder;

@Component
public class Renderer {

    @Autowired
    private SpriteRepository spriteRepository;
    @Autowired
    private ResourceKeeper resourceKeeper;
    @Autowired
    private GameContext gameContext;
    @Autowired
    private EventContext eventContext;

    private Map<String, Phase> cache = new HashMap<>();
    private boolean isPause;

    @Data
    private class Phase {
        private int phaseNumber = 0;
        private BehaviorState state = IDLE;

        public void incPhaseNumber() {
            phaseNumber++;
        }

        public void defPhaseNumber() {
            phaseNumber = 0;
        }
    }

    /**
     * Calculates and returns frames should be painted on game screen.
     */
    public Frame calcFrame(ViewContext viewContext) {
        Game game = gameContext.getGame();
        double playerX = game.getPlayer().getX();
        double playerY = game.getPlayer().getY();

        Frame frame = new Frame();

        String backSignature = game.getPlayer().getLocation().getSignature();
        Sprite background = calcBackground(backSignature, playerX, playerY);
        frame.setBackground(background);

        List<AbstractPhysicalObject> objects = game.getPlayer().getLocation().getObjects();
        List<Sprite> sprites = new ArrayList<>();
        for (AbstractPhysicalObject o : objects) {
            Sprite sprite = calcSprite(o, playerX, playerY);
            sprites.add(sprite);
        }
        frame.setSprites(calcRenderOrder(sprites));

        calcEffects(frame, viewContext);
        calcLogs(frame);
        calcMarker(frame, viewContext, playerX, playerY);
        calcProfile(frame);
        calcArea(frame, viewContext);

        return frame;
    }

    /**
     * Toggles active setPause.
     *
     * @param isPause   active pause sign.
     */
    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    /**
     * Fills profile widget in frame.
     *
     * @param frame frame that contains all rendered information for drawing.
     */
    private void calcProfile(Frame frame) {
        Player player = gameContext.getPlayer();
        Profile profile = player.getProfile();
        frame.getProfileWidget().mapFromProfile(profile);
    }

    /**
     * Fills logs in frame.
     *
     * @param frame frame that contains all rendered information for drawing.
     */
    private void calcLogs(Frame frame) {
        eventContext.getEvents()
                .stream()
                .forEach(e -> {
                    if (e.displayable())
                        frame.addLog(e.toString());
                });
    }

    /**
     * Renders destination marker
     *
     * @param frame       frame that contains all rendered information for drawing.
     * @param viewContext view context that contains states of visual objects on game screen.
     * @param playerX     horizontal coordinate of the player.
     * @param playerY     vertical coordinate of the player.
     */
    private void calcMarker(Frame frame, ViewContext viewContext, double playerX, double playerY) {
        DestinationMarkerData marker = viewContext.getMarker();
        if (marker != null) {

            int imgX = (int) (CANVAS_CENTER_X - (playerX - marker.getX()));
            int imgY = (int) (CANVAS_CENTER_Y - (playerY - marker.getY()));

            DestinationMarkerEffect markerEffect = new DestinationMarkerEffect();
            markerEffect
                    .setX(imgX)
                    .setY(imgY);
            frame.setMarker(markerEffect);
        }
    }

    /**
     * Calculates and puts visual effects to frame.
     *
     * @param frame       frame that contains all rendered information for drawing.
     * @param viewContext view context that contains states of visual objects on game screen.
     */
    private void calcEffects(Frame frame, ViewContext viewContext) {
        // todo: check for possible race condition!
        Coordinate mousePoint = viewContext.getScreenPoint();
        int screenX = mousePoint.getX();
        int screenY = mousePoint.getY();

        if (frame != null) {
            Sprite sprite = getSpriteAtScreenCoordinates(frame.getSprites(), screenX, screenY);
            if (sprite != null) {
                frame.addHighLighted(sprite.getId(), true);
            }
        }
    }

    /**
     * Renders area under mouse cursor if any.
     *
     * @param frame       frame that contains all rendered information for drawing.
     * @param viewContext view context that contains states of visual objects on game screen.
     */
    public void calcArea(Frame frame, ViewContext viewContext) {
        Game game = gameContext.getGame();
        Coordinate mousePoint = viewContext.getScreenPoint();
        int screenX = mousePoint.getX();
        int screenY = mousePoint.getY();
        int playerX = game.getPlayer().getX();
        int playerY = game.getPlayer().getY();
        int gameX = playerX + screenX - CANVAS_CENTER_X;
        int gameY = playerY + screenY - CANVAS_CENTER_Y;

        game.getPlayer().getLocation().getAreas().stream()
                .filter(a -> CoordinateUtils.isPointInner(gameX, gameY, a.getCoordinates()))
                .findFirst()
                .map(a -> {
                    AreaHighlighted areaHighlighted = new AreaHighlighted();

                    // map coordinates from game to screen.
                    List<Coordinate> gameCoordinates = a.getCoordinates()
                            .stream()
                            .map(c -> new Coordinate()
                                    .setX(c.getX() + CANVAS_CENTER_X - playerX)
                                    .setY(c.getY() + CANVAS_CENTER_Y - playerY))
                            .collect(Collectors.toList());

                    areaHighlighted.getCoordinates().addAll(gameCoordinates);
                    areaHighlighted.setMapArea(a);
                    frame.setAreaHighlighted(areaHighlighted);
                    return null;
                });
    }

    private Sprite calcBackground(String signature, double playerX, double playerY) {
        Resource resource = new Resource(signature, 0, 0, 0, 0);
        Image image = spriteRepository.getImage(resource);

        int imgX = (int) ((CANVAS_CENTER_X - (playerX)) + image.getWidth() / 2);
        int imgY = (int) ((CANVAS_CENTER_Y - (playerY)) + image.getHeight());

        return new Sprite(image)
                .setX(imgX)
                .setY(imgY)
                .setWidth((int) image.getWidth())
                .setHeight((int) image.getHeight())
                .setPlatformShape(PlatformShape.CUSTOM);
    }

    private Sprite calcSprite(AbstractPhysicalObject object, double playerX, double playerY) {
        int centerX = CANVAS_WIDTH / 2;
        int centerY = CANVAS_HEIGHT / 2;

        Resource resource = calcResource(object);
        Image image = spriteRepository.getImage(resource);

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
                .setWidth(object.getWidth());
    }

    private Resource calcResource(AbstractPhysicalObject object) {
        Phase phase = cache.get(object.getUniqueName());
        if (phase == null) {
            phase = new Phase();
            cache.put(object.getUniqueName(), phase);
        }

        if (!isPause) {
            AbstractCommand command = object.getCommand();
            BehaviorState newState = command != null ? object.getCommand().getState() : IDLE;
            if (phase.getState() != newState) {
                phase.defPhaseNumber();
                phase.setState(newState);
            } else {
                phase.incPhaseNumber();
            }
        }

        int phaseNumber = 1 + (phase.getPhaseNumber() / ANIMATION_SLOW_FACTOR);
        BehaviorState state = object.getCommand() != null ? object.getCommand().getState() : IDLE;
        if (!resourceKeeper.isResourceExists(object.getSignature(), state, object.getDirection(), phaseNumber)) {
            phase.defPhaseNumber();
            phaseNumber = 1;
        }
        return resourceKeeper.getResource(object.getSignature(), state, object.getDirection(), phaseNumber);
    }
}