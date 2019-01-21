package neverless.repository.cache;

import neverless.domain.entity.Game;
import neverless.repository.persistence.GameRepository;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache for game object.
 * Stores game after first load
 */
@Repository
public class GameCache {

    @Autowired
    private GameRepository repository;
    @Autowired
    private SessionUtil sessionUtil;

    private Map<String, Game> cache = new ConcurrentHashMap<>();

    /**
     * Returns game object from cache.
     * Loads game object from persistence layer if the object canProcessObject absent in cache.
     */
    public Game getGame() {
        String id = sessionUtil.getGameId();
        Game game = cache.get(id);
        if (game == null) {
            game = repository.findById(sessionUtil.getGameId())
                    .orElseThrow(RuntimeException::new);
            cache.put(id, game);
        }
        return game;
    }
}