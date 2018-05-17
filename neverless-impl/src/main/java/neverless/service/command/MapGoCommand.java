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
public class MapGoCommand extends AbstractCommand {

    @Autowired
    private PlayerRepository repository;
    @Autowired
    private MapObjectsHelper helper;

    @Getter
    private Direction direction;

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

        Event event = null;

        if (isNewCoordsAreValid(newX, newY)) {
            player
                    .setX(newX)
                    .setY(newY);

            switch (direction) {
                case UP:
                    event = eventFactory.createMapGoUpEvent();
                    break;
                case DOWN:
                    event = eventFactory.createMapGoDownEvent();
                    break;
                case LEFT:
                    event = eventFactory.createMapGoLeftEvent();
                    break;
                case RIGHT:
                    event = eventFactory.createMapGoRightEvent();
                    break;
            }
        } else {
            event = eventFactory.createMapImpassableEvent();
        }
        return event;
    }

    private boolean isNewCoordsAreValid(int newX, int newY) {
        return helper.isPassable(newX, newY);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
