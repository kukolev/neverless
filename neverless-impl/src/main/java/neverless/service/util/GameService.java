package neverless.service.util;

import neverless.domain.entity.Game;
import neverless.repository.persistence.GameRepository;
import neverless.repository.cache.GameCache;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameCache gameRepository2;
    @Autowired
    private SessionUtil sessionUtil;

    /**
     * Loads game object from repository and returns it.
     */
    public Game getGame() {
        return gameRepository2.getGame();
    }
}
