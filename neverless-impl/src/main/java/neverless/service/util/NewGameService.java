package neverless.service.util;

import neverless.game.GameLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewGameService {

    @Autowired
    private GameLoader loader;

    public void startNewGame() {
        loader.createNewGame();
    }
}
