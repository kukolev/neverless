package neverless.service.screendata;

import neverless.domain.mapobject.AbstractMapObject;
import neverless.domain.mapobject.Player;
import neverless.dto.command.Direction;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.service.core.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static neverless.Constants.LOCAL_MAP_HEIGH;
import static neverless.Constants.LOCAL_MAP_PLAYER_X;
import static neverless.Constants.LOCAL_MAP_PLAYER_Y;
import static neverless.Constants.LOCAL_MAP_WIDTH;
import static neverless.Constants.PLAYER_ID;

@Service
public class LocalMapService extends AbstractService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MapObjectsRepository mapObjRepository;
    @Autowired
    private EventContext eventContext;

    public void mapGo(Direction direction) {
        Player player = playerRepository.get(PLAYER_ID);

        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case UP: newY--; break;
            case DOWN: newY++; break;
            case LEFT: newX--; break;
            case RIGHT: newX++; break;
        }

        if (isPassable(newX, newY)) {
            player.setX(newX);
            player.setY(newY);

            eventContext.addMapGoEvent(direction);
        } else {
            eventContext.addMapGoImpossibleEvent();
        }
    }

    public LocalMapScreenDataDto getScreenData() {
        LocalMapScreenDataDto localMapScreenDataDto = createCleanMap();
        Player player = playerRepository.get(PLAYER_ID);
        if (player == null) {
            return localMapScreenDataDto;
        }
        final int screenX1 = player.getX() - LOCAL_MAP_PLAYER_X;
        final int screenY1 = player.getY() - LOCAL_MAP_PLAYER_Y;

        List<AbstractMapObject> objects = getObjectsForRender();
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

    private List<AbstractMapObject> getObjectsForRender() {
        List<AbstractMapObject> result = new ArrayList<>();
        List<AbstractMapObject> mapObjects = mapObjRepository.findAll();

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

    private boolean isPassable(int x, int y) {
        AbstractMapObject mapObject = getMapObjectAtPosition(x, y);
        return (mapObject == null || mapObject.isPassable());
    }

    private AbstractMapObject getMapObjectAtPosition(int x, int y) {
        return mapObjRepository.findAll()
                .stream()
                .filter(object -> (object.getX() == x) && (object.getY() == y))
                .findFirst()
                .orElse(null);
    }
}
