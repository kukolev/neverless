package neverless.service.screendata;

import neverless.game.GameLoader;
import neverless.util.EventFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewGameService extends AbstractService {

    @Autowired
    private GameLoader loader;
    @Autowired
    private EventFactory eventFactory;

    public void startNewGame() {
        loader.createNewGame();
        registerEvent(eventFactory.createMapGoImpossibleEvent());
    }
}
