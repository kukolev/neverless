package neverless.service.screendata;

import neverless.game.GameLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class NewGameService {

    @Autowired
    private GameLoader loader;

    public void startNewGame() {
        loader.createNewGame();
    }
}
