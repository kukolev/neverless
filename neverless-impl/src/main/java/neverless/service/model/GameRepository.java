package neverless.service.model;

import neverless.domain.model.entity.Game;
import neverless.domain.model.entity.Location;
import neverless.domain.model.entity.mapobject.AbstractPhysicalObject;
import neverless.domain.model.entity.mapobject.Player;
import org.springframework.stereotype.Repository;

import java.util.Random;

/**
 * Cache for game object.
 * Stores game after first load
 */
@Repository
public class GameRepository {

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

    /**
     * Removes object from game
     *
     * @param object
     */
    public void removePhysicalObject(AbstractPhysicalObject object) {
        game.getLocations()
                .forEach(location -> {
                    location.getObjects().remove(object);
                    object.setLocation(null);
                });
    }

    /**
     * Adds object to location.
     *
     * @param object        object that should be added.
     * @param location      location where object should appears.
     */
    public void addPhysicalObject(AbstractPhysicalObject object, Location location) {
        location.getObjects().add(object);
        object.setLocation(location);
    }
}














