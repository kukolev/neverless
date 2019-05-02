package neverless.context;

import neverless.domain.model.entity.Game;
import neverless.domain.model.entity.mapobject.Player;
import org.springframework.stereotype.Repository;

/**
 * Cache for game object.
 * Stores game after first load
 */
@Repository
public class GameContext {

    private Game game;

    /**
     * Returns game object from cache.
     * Loads game object from persistence layer if the object is absent in cache.
     */
    public Game getGame() {
       return game;
    }

    public Player getPlayer() {
        return getGame().getPlayer();
    }

    public void save(Game game) {
       this.game = game;
    }
}