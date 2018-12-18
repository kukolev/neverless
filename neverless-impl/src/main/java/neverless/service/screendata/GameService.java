package neverless.service.screendata;

import neverless.domain.Game;
import neverless.repository.GameRepository;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private SessionUtil sessionUtil;

    /**
     * Loads game object from repository and returns it.
     */
    public Game getGame() {
        return gameRepository
                .findById(sessionUtil.getGameId())
                .orElseThrow(RuntimeException::new);
    }
}
