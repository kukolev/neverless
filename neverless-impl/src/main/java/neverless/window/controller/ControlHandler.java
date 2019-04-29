package neverless.window.controller;

import neverless.context.GameContext;
import neverless.domain.entity.mapobject.portal.LocationPortal;
import neverless.model.Model;
import neverless.service.command.factory.GameCommandFactory;
import neverless.service.command.factory.PlayerCommandFactory;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.util.FrameExchanger;
import neverless.util.FrameUtils;
import neverless.view.domain.Frame;
import neverless.view.domain.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static neverless.util.Constants.CANVAS_CENTER_X;
import static neverless.util.Constants.CANVAS_CENTER_Y;
import static neverless.util.FrameUtils.getSpriteAtScreenCoordinates;

@Component
public class ControlHandler {

    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private Model model;
    @Autowired
    private PlayerCommandFactory commandFactory;
    @Autowired
    private GameCommandFactory gameCommandFactory;
    @Autowired
    private GameContext gameContext;

    /**
     * Evaluates a command that should be performed by clicking in some coordinates.
     * Puts the command in queue for further processing in Model.
     *
     * @param screenX screen horizontal coordinate.
     * @param screenY screen vertical coordinate.
     */
    public void click(int screenX, int screenY) {
        model.setScreenPoint(screenX, screenY);
        Frame frame = frameExchanger.getFrame();
        Sprite sprite = getSpriteAtScreenCoordinates(frame.getSprites(), screenX, screenY);

        // Convert screen coordinates to game coordinates
        Player player = gameContext.getPlayer();
        int dx = screenX - CANVAS_CENTER_X;
        int dy = screenY - CANVAS_CENTER_Y;
        int newGameX = player.getX() + dx;
        int newGameY = player.getY() + dy;

        // Updates destination marker
        model.setDestinationMarker(newGameX, newGameY);

        if (sprite != null) {
            // Click on sprite
            if (sprite.getMapObject() instanceof AbstractEnemy) {
                cmdFightingAttack((AbstractEnemy) sprite.getMapObject());
            }
        } else if (FrameUtils.isAreaAtScreenCoordinates(frame.getAreaHighlighted(), screenX, screenY)) {
            // Click on area
            if (frame.getAreaHighlighted().getMapArea() instanceof LocationPortal) {
                LocationPortal portal = (LocationPortal) frame.getAreaHighlighted().getMapArea();
                model.putCommand(commandFactory.createPlayerPortalEnterCommand(portal));
            }
        } else {
            // Click on background
            model.putCommand(commandFactory.createPlayerMapGoCommand(newGameX, newGameY));
        }
    }

    public void mouseMove(int screenX, int screenY) {
        model.setScreenPoint(screenX, screenY);
    }

    /**
     * Creates command for new game start.
     */
    public void cmdStartNewGame() {
        model.putCommand(gameCommandFactory.createStartNewGameCommand());
        new Thread(model).start();
    }

    private void cmdFightingAttack(AbstractEnemy enemy) {
        model.putCommand(commandFactory.createPlayerAttackCommand(enemy));
    }

    /**
     * Toggle active pause.
     */
    public void pause() {
        model.pause();
    }
}