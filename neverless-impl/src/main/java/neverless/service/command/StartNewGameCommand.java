package neverless.service.command;

import lombok.AllArgsConstructor;
import neverless.domain.mapobject.Player;
import neverless.repository.PlayerRepository;
import neverless.game.GameLoader;
import neverless.util.EventFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartNewGameCommand extends AbstractCommand<EmptyParams> {

    private GameLoader loader;
    private EventFactory eventFactory;

    @Override
    public void execute(EmptyParams params) {

        loader.createNewGame();
        registerEvent(eventFactory.createMapGoImpossibleEvent());
    }
}
