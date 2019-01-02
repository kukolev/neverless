package neverless.service.screendata;

import neverless.PlatformShape;
import neverless.domain.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.Direction;
import neverless.dto.CoordinateDto;
import neverless.dto.MapObjectDto;
import neverless.dto.LocalMapScreenDataDto;
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
import static neverless.util.CoordinateUtils.isCurvesIntersected;

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
        if (isPassable(player, x, y)) {
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
        localMapScreenDataDto.setSignature(player.getLocation().getSignature());
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
                .setPlatformHeight(mapObject.getPlatformHeight())
                .setPlatformWidth(mapObject.getPlatformWidth())
                .setPlatformCenterX(mapObject.getPlatformCenterX())
                .setPlatformCenterY(mapObject.getPlatformCenterY())
                .setMetaType(mapObject.getMetaType())
                .setPlatformShape(mapObject.getPlatformShape())
                .setPlatformCoordinates(mapCoordinates(mapObject.getPlatformCoordinates()));
    }

    private List<CoordinateDto> mapCoordinates(List<Coordinate> coordinates) {
        return coordinates.stream()
                .map(c -> new CoordinateDto()
                        .setX(c.getX())
                        .setY(c.getY()))
                .collect(Collectors.toList());
    }

    public boolean isPassable(int x, int y, Location location) {
        // todo: remove it if obsolete or refactor it.
        return true;
    }

    public boolean isPassable(AbstractMapObject walker, int newX, int newY) {

        boolean intersection = false;
        for (int i = 0; i < walker.getLocation().getObjects().size(); i++) {
            AbstractMapObject object = walker.getLocation().getObjects().get(i);
            if (object.getPlatformShape() == PlatformShape.CUSTOM) {
                if (walker.getPlatformShape() == PlatformShape.ELLIPSE) {

                    List<Coordinate> realCoordinates = object.getPlatformCoordinates()
                            .stream()
                            .map(c -> new Coordinate()
                                    .setX(object.getX() + c.getX())
                                    .setY(object.getY() + c.getY()))
                            .collect(Collectors.toList());

                    int ellipseCenterX = newX + walker.getPlatformCenterX();
                    int ellipseCenterY = newY + walker.getPlatformCenterY();


                    intersection = isCurvesIntersected(ellipseCenterX, ellipseCenterY, walker.getPlatformWidth() / 2, walker.getPlatformHeight() / 2, realCoordinates);
                    if (intersection) {
                        break;
                    }
                }
            }
        }
        return !intersection;
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
