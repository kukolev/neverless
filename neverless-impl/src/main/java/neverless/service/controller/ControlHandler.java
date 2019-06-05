package neverless.service.controller;

import neverless.domain.model.entity.mapobject.loot.LootContainer;
import neverless.service.model.GameRepository;
import neverless.core.GameLoop;
import neverless.domain.model.entity.mapobject.portal.LocationPortal;
import neverless.service.model.command.factory.GameCommandFactory;
import neverless.service.model.command.factory.PlayerCommandFactory;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.enemy.AbstractEnemy;
import neverless.service.FrameExchanger;
import neverless.service.view.FrameUtils;
import neverless.domain.view.Frame;
import neverless.domain.view.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static neverless.util.Constants.CANVAS_CENTER_X;
import static neverless.util.Constants.CANVAS_CENTER_Y;
import static neverless.service.view.FrameUtils.getSpriteAtScreenCoordinates;

@Component
public class ControlHandler {

    @Autowired
    private FrameExchanger frameExchanger;
    @Autowired
    private GameLoop gameLoop;
    @Autowired
    private PlayerCommandFactory commandFactory;
    @Autowired
    private GameCommandFactory gameCommandFactory;
    @Autowired
    private GameRepository gameRepository;

    /**
     * Evaluates a command that should be performed by clicking in some coordinates.
     * Puts the command in queue for further processing in GameLoop.
     *
     * @param screenX screen horizontal coordinate.
     * @param screenY screen vertical coordinate.
     */
    public void click(int screenX, int screenY) {
        gameLoop.setScreenPoint(screenX, screenY);
        Frame frame = frameExchanger.getFrame();
        Sprite sprite = getSpriteAtScreenCoordinates(frame.getSprites(), screenX, screenY);

        // Convert screen coordinates to game coordinates
        Player player = gameRepository.getPlayer();
        int dx = screenX - CANVAS_CENTER_X;
        int dy = screenY - CANVAS_CENTER_Y;
        int newGameX = player.getX() + dx;
        int newGameY = player.getY() + dy;

        // Updates destination marker
        gameLoop.setDestinationMarker(newGameX, newGameY);

        if (sprite != null && sprite.getMapObject().isHighlightable()) {
            // Click on sprite
            if (sprite.getMapObject() instanceof AbstractEnemy) {
                cmdFightingAttack((AbstractEnemy) sprite.getMapObject());
            }
            if (sprite.getMapObject() instanceof LootContainer) {
                cmdTakeLootContainer((LootContainer) sprite.getMapObject());
            }
        } else if (FrameUtils.isAreaAtScreenCoordinates(frame.getAreaHighlighted(), screenX, screenY)) {
            // Click on area
            if (frame.getAreaHighlighted().getMapArea() instanceof LocationPortal) {
                LocationPortal portal = (LocationPortal) frame.getAreaHighlighted().getMapArea();
                gameLoop.putCommand(commandFactory.createPlayerPortalEnterCommand(portal));
            }
        } else {
            // Click on background
            gameLoop.putCommand(commandFactory.createPlayerMapGoCommand(newGameX, newGameY));
        }
    }

    public void mouseMove(int screenX, int screenY) {
        gameLoop.setScreenPoint(screenX, screenY);
    }

    /**
     * Creates command for new game start.
     */
    public void cmdStartNewGame() {
        gameLoop.putCommand(gameCommandFactory.createStartNewGameCommand());
        new Thread(gameLoop).start();
    }

    private void cmdFightingAttack(AbstractEnemy enemy) {
        gameLoop.putCommand(commandFactory.createPlayerAttackCommand(enemy));
    }

    private void cmdTakeLootContainer(LootContainer lootContainer) {
        gameLoop.putCommand(commandFactory.createPlayerTakeLootCommand(lootContainer));
    }

    /**
     * Toggle active pause.
     */
    public void pause() {
        gameLoop.pause();
    }
}