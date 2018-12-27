package neverless.service.screendata;

import neverless.domain.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.dto.command.Direction;
import neverless.dto.screendata.MapObjectDto;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.context.EventContext;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;

@Service
@Transactional
public class LocalMapService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MapObjectsRepository mapObjRepository;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private PlayerService playerService;

    public void mapGo(Direction direction) {
        Player player = playerService.getPlayer();

        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case UP: newY -= LOCAL_MAP_STEP_LENGTH; break;
            case DOWN: newY += LOCAL_MAP_STEP_LENGTH; break;
            case LEFT: newX -= LOCAL_MAP_STEP_LENGTH; break;
            case RIGHT: newX += LOCAL_MAP_STEP_LENGTH; break;
            case UP_LEFT: {
                newY -= LOCAL_MAP_STEP_LENGTH;
                newX -= LOCAL_MAP_STEP_LENGTH;
                break;
            }
            case UP_RIGHT: {
                newY -= LOCAL_MAP_STEP_LENGTH;
                newX += LOCAL_MAP_STEP_LENGTH;
                break;
            }
            case DOWN_LEFT: {
                newY += LOCAL_MAP_STEP_LENGTH;
                newX -= LOCAL_MAP_STEP_LENGTH;
                break;
            }
            case DOWN_RIGHT: {
                newY += LOCAL_MAP_STEP_LENGTH;
                newX += LOCAL_MAP_STEP_LENGTH;
                break;
            }
        }
        mapGo(newX, newY, direction);
    }

    private void mapGo(int x, int y, Direction direction) {
        Player player = playerService.getPlayer();

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
        playerRepository.save(player);
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
        eventContext.addPortalEnterEvent(portal.getDestination().getTitle());
    }

    public LocalMapScreenDataDto getScreenData() {
        LocalMapScreenDataDto localMapScreenDataDto = new LocalMapScreenDataDto();
        Player player = playerService.getPlayer();
        if (player == null) {
            return localMapScreenDataDto;
        }
        List<MapObjectDto> objectDtos = getAllMapObjects(player.getLocation())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        objectDtos.add(mapToDto(player));
        localMapScreenDataDto.setObjects(objectDtos);
        return localMapScreenDataDto;
    }

    public List<AbstractMapObject> getAllMapObjects(Location location) {
        List<AbstractMapObject> result = new ArrayList<>();
        // collect objects from lists
        result.addAll(location.getObjects());
        result.addAll(location.getNpcs());
        result.addAll(location.getRespawnPoints());
        result.addAll(location.getPortals());

        // collect enemies
        result.addAll(location.getRespawnPoints()
                .stream()
                .map(rp -> rp.getEnemy())
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        return result;
    }

    /**
     * Converts map object to dto and returns last one.
     * Calculates and adds meta type information about the object;
     *
     * @param mapObject object that should be converted
     */
    private MapObjectDto mapToDto(AbstractMapObject mapObject) {
        return new MapObjectDto()
                .setUniqueName(mapObject.getUniqueName())
                .setSignature(mapObject.getSignature())
                .setX(mapObject.getX())
                .setY(mapObject.getY())
                .setHeight(mapObject.getHeight())
                .setWidth(mapObject.getWidth())
                .setZOrder(mapObject.getZOrder())
                .setMetaType(mapObject.getMetaType())
                .setPlatformShape(mapObject.getPlatformShape());
    }

    public boolean isPassable(int x, int y, Location location) {
        Player player = playerService.getPlayer();
        boolean isPlayer = player.getX().equals(x) && player.getY().equals(y);
        AbstractMapObject mapObject = getMapObjectAtPosition(x, y, location);

        return (!isPlayer) && (mapObject == null || mapObject.isPassable());
    }

    private boolean isPortal(int x, int y, Location location) {
        AbstractMapObject mapObject = getMapObjectAtPosition(x, y, location);
        return mapObject instanceof AbstractPortal;
    }

    private AbstractMapObject getMapObjectAtPosition(int x, int y, Location location) {
        return location.getObjects()
                .stream()
                .filter(object -> (object.getX() == x) && (object.getY() == y))
                .findFirst()
                .orElse(null);
    }
}
