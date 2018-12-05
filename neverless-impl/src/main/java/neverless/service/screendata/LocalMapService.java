package neverless.service.screendata;

import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.domain.entity.mapobject.npc.AbstractNpc;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.dto.MapObjectMetaType;
import neverless.dto.command.Direction;
import neverless.dto.screendata.MapObjectDto;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.context.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static neverless.Constants.LOCAL_MAP_HEIGH;
import static neverless.Constants.LOCAL_MAP_PLAYER_X;
import static neverless.Constants.LOCAL_MAP_PLAYER_Y;
import static neverless.Constants.LOCAL_MAP_WIDTH;

@Service
@Transactional
public class LocalMapService {

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
        LocalMapScreenDataDto localMapScreenDataDto = new LocalMapScreenDataDto();
        Player player = playerRepository.get();
        if (player == null) {
            return localMapScreenDataDto;
        }

        List<AbstractMapObject> objects = mapObjRepository.findAllByLocation(player.getLocation());
        List<MapObjectDto> objectDtos = objects.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        localMapScreenDataDto.setObjects(objectDtos);
        return localMapScreenDataDto;
    }

    /**
     * Converts map object to dto and returns last one.
     * Calculates and adds meta type information about the object;
     *
     * @param mapObject object that should be converted
     */
    private MapObjectDto mapToDto(AbstractMapObject mapObject) {
        MapObjectDto dto = new MapObjectDto();
        dto
                .setUniqueName(mapObject.getUniqueName())
                .setSignature(mapObject.getSignature())
                .setX(mapObject.getX())
                .setY(mapObject.getY())
                .setHeight(mapObject.getHeight())
                .setWidth(mapObject.getWidth())
                .setZOrder(mapObject.getZOrder())
                .setMetaType(mapObject.getMetaType());

        // todo: maybe it should be optimized. for example store meta type in class
        MapObjectMetaType metaType = MapObjectMetaType.IMPASSIBLE_TERRAIN;
        if (mapObject.isPassable()) {
            metaType = MapObjectMetaType.TERRAIN;
        } else if (mapObject instanceof AbstractNpc) {
            metaType = MapObjectMetaType.NPC;
        } else if (mapObject instanceof AbstractEnemy) {
            metaType = MapObjectMetaType.ENEMY;
        } else if (mapObject instanceof AbstractPortal) {
            metaType = MapObjectMetaType.PORTAL;
        }
        dto.setMetaType(metaType);

        return dto;
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
