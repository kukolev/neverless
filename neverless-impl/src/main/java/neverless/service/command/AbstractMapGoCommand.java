package neverless.service.command;

import lombok.Getter;
import neverless.dto.command.Direction;
import neverless.domain.event.Event;
import neverless.domain.mapobject.Player;
import neverless.repository.PlayerRepository;
import neverless.service.MapObjectsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static neverless.Constants.PLAYER_ID;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class AbstractMapGoCommand extends AbstractCommand {

    @Autowired
    private PlayerRepository repository;
    @Autowired
    private MapObjectsHelper helper;

    protected Direction direction;

    @Override
    public Event onExecute() {
        Player player = repository.get(PLAYER_ID);

        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case UP: newY--; break;
            case DOWN: newY++; break;
            case LEFT: newX--; break;
            case RIGHT: newX++; break;
        }

        if (isNewCoordsAreValid(newX, newY)) {
            player.setX(newX);
            player.setY(newY);
            return eventFactory.createMapGoEvent();
        } else {
            return eventFactory.createMapImpassableEvent();
        }
    }

    private boolean isNewCoordsAreValid(int newX, int newY) {
        return helper.isPassable(newX, newY);
    }
}
