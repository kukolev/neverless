package neverless.service.command;

import neverless.domain.mapobject.Player;
import neverless.repository.PlayerRepository;
import neverless.service.MapObjectsHelper;
import neverless.util.EventFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static neverless.Constants.PLAYER_ID;

@Component
public class MapGoCommand extends AbstractCommand<MapGoParams> {

    @Autowired
    private PlayerRepository repository;
    @Autowired
    private MapObjectsHelper helper;
    @Autowired
    private EventFactory eventFactory;

    @Override
    public void execute (MapGoParams params) {
        Player player = repository.get(PLAYER_ID);

        int newX = player.getX();
        int newY = player.getY();

        switch (params.getDirection()) {
            case UP: newY--; break;
            case DOWN: newY++; break;
            case LEFT: newX--; break;
            case RIGHT: newX++; break;
        }

        if (isNewCoordsAreValid(newX, newY)) {
            player.setX(newX);
            player.setY(newY);

            registerEvent(eventFactory.createMapGoEvent(params.getDirection()));
        } else {
            registerEvent(eventFactory.createMapGoImpossibleEvent());
        }
    }

    private boolean isNewCoordsAreValid(int newX, int newY) {
        return helper.isPassable(newX, newY);
    }
}
