package neverless.service.screendata;

import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.dto.command.Direction;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.service.core.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static neverless.Constants.LOCAL_MAP_HEIGH;
import static neverless.Constants.LOCAL_MAP_PLAYER_X;
import static neverless.Constants.LOCAL_MAP_PLAYER_Y;
import static neverless.Constants.LOCAL_MAP_WIDTH;

@Service
@Transactional
public class LocalMapService extends AbstractService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MapObjectsRepository mapObjRepository;
    @Autowired
    private EventContext eventContext;

    public void mapGo(Direction direction) {
        Player player = playerRepository.get();

        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case UP: newY--; break;
            case DOWN: newY++; break;
            case LEFT: newX--; break;
            case RIGHT: newX++; break;
        }
        mapGo(newX, newY, direction);
    }

    private void mapGo(int x, int y, Direction direction) {
        Player player = playerRepository.get();

        if (isPortal(x, y, player.getLocation())) {
            doPortalEnter(player, x, y);
            return;
        }
        if (isPassable(x, y, player.getLocation())) {
            doMoving(player, x, y, direction);
            return;
        }
        doImpossibleMove();
    }

    private void doMoving(Player player, int x, int y, Direction direction) {
        player.setX(x);
        player.setY(y);
        eventContext.addMapGoEvent(direction);
    }

    private void doImpossibleMove() {
        eventContext.addMapGoImpossibleEvent();
    }

    private void doPortalEnter(Player player, int x, int y) {
        AbstractPortal portal = (AbstractPortal) getMapObjectAtPosition(x, y, player.getLocation());
        player
                .setLocation(portal.getDestination())
                .setX(portal.getDestX())
                .setY(portal.getDestY());
        eventContext.addPortalEnterEvent(portal.getDestination());
    }

    public LocalMapScreenDataDto getScreenData() {
        LocalMapScreenDataDto localMapScreenDataDto = createCleanMap();
        Player player = playerRepository.get();
        if (player == null) {
            return localMapScreenDataDto;
        }
        final int screenX1 = player.getX() - LOCAL_MAP_PLAYER_X;
        final int screenY1 = player.getY() - LOCAL_MAP_PLAYER_Y;

        List<AbstractMapObject> objects = getObjectsForRender(player.getLocation());
        objects.stream()
                .sorted(Comparator.comparingInt(AbstractMapObject::getZOrder))
                .forEach(obj -> {
                    int objPart = 0;
                    for (int j = obj.getY(); j < obj.getY() + obj.getHeight(); j++)
                        for (int i = obj.getX(); i < obj.getX() + obj.getWidth(); i++) {

                            objPart++;
                            int localX = i - screenX1;
                            int localY = j - screenY1;

                            if ((localX >= 0) && (localY >= 0) && (localX < LOCAL_MAP_WIDTH)  && (localY < LOCAL_MAP_HEIGH)) {
                                localMapScreenDataDto.setCell(localX, localY, obj.getSignature() + objPart);
                            }
                        }
                });

        localMapScreenDataDto.setCell(LOCAL_MAP_PLAYER_X, LOCAL_MAP_PLAYER_Y, player.getSignature());
        return localMapScreenDataDto;
    }

    private List<AbstractMapObject> getObjectsForRender(String location) {
        List<AbstractMapObject> result = new ArrayList<>();
        List<AbstractMapObject> mapObjects = mapObjRepository.findAllByLocation(location);

        // TODO: Add real filtration for objects which should render
        result.addAll(mapObjects);

        return result;
    }

    private LocalMapScreenDataDto createCleanMap() {
        LocalMapScreenDataDto localMapScreenDataDto = new LocalMapScreenDataDto();
        for (int i = 0; i < LOCAL_MAP_WIDTH; i++)
            for (int j = 0; j < LOCAL_MAP_HEIGH; j++) {
                localMapScreenDataDto.setCell(i, j, "~");
            }
        return localMapScreenDataDto;
    }

    public boolean isPassable(int x, int y, String location) {
        Player player = playerRepository.get();
        boolean isPlayer = player.getX().equals(x) && player.getY().equals(y);
        AbstractMapObject mapObject = getMapObjectAtPosition(x, y, location);

        return (!isPlayer) && (mapObject == null || mapObject.isPassable());
    }

    private boolean isPortal(int x, int y, String location) {
        AbstractMapObject mapObject = getMapObjectAtPosition(x, y, location);
        return mapObject instanceof AbstractPortal;
    }

    private AbstractMapObject getMapObjectAtPosition(int x, int y, String location) {
        return mapObjRepository.findAllByLocation(location)
                .stream()
                .filter(object -> (object.getX() == x) && (object.getY() == y))
                .findFirst()
                .orElse(null);
    }
}
