package neverless.service.command;

import neverless.domain.constants.DirectionEnum;
import neverless.domain.event.Event;
import neverless.domain.game.mapobject.Player;
import neverless.domain.repository.PlayerRepository;
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

    private DirectionEnum direction;

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

        validateNewCoords(newX, newY);
        player.setX(newX)
                .setY(newY);

        Event event = null;
        switch (direction) {
            case UP: event = eventFactory.createMapGoUpEvent(); break;
            case DOWN: eventFactory.createMapGoDownEvent(); break;
            case LEFT: eventFactory.createMapGoLeftEvent(); break;
            case RIGHT: eventFactory.createMapGoRightEvent(); break;
        }

        return event;
    }

    private void validateNewCoords(int newX, int newY) {
        // todo: implement validation
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }
}
