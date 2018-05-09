package neverless.service.command;

import lombok.AllArgsConstructor;
import neverless.domain.event.Event;
import neverless.domain.mapobject.Player;
import neverless.repository.PlayerRepository;
import neverless.game.GameLoader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MenuStartNewGameCommand extends AbstractCommand {

    private GameLoader loader;
    private PlayerRepository playerRepository;

    @Override
    public Event onExecute() {

        loader.createLandscape();
        Player player = new Player();
        player.setUniqueName("Vova");
        player.setX(50)
                .setY(50);
        playerRepository.save(player);

        return null;
    }
}
