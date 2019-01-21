package neverless.service.util;

import neverless.domain.entity.Game;
import neverless.repository.cache.GameCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameCache gameRepository;


    /**
     * Loads game object from repository and returns it.
     */
    public Game getGame() {
        return gameRepository.getGame();
    }
}
