package neverless.service.util;

import neverless.PlatformShape;
import neverless.context.EventContext;
import neverless.context.GameContext;
import neverless.domain.entity.mapobject.AbstractLiveObject;
import neverless.domain.entity.mapobject.AbstractPhysicalObject;
import neverless.util.Coordinate;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.portal.LocationPortal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static neverless.util.CoordinateUtils.calcDirection;
import static neverless.util.CoordinateUtils.calcNextStep;
import static neverless.util.CoordinateUtils.isCurvesIntersected;

@Service
public class LocalMapService {

    @Autowired
    private EventContext eventContext;
    @Autowired
    private GameContext gameContext;

    /**
     * Returns true if walker can go to new coordinates.
     *
     * @param walker    walker.
     * @param newX      new horizontal coordinate where walker wants to go to
     * @param newY      new vertical coordinate where walker wants to go to
     */
    public boolean isPassable(AbstractPhysicalObject walker, int newX, int newY) {
        boolean intersection = false;

        for (AbstractPhysicalObject object: walker.getLocation().getObjects()) {
            if (walker.equals(object)) {
                continue;
            }

            if (object.getPlatformShape() == PlatformShape.CUSTOM) {
                if (walker.getPlatformShape() == PlatformShape.ELLIPSE) {

                    List<Coordinate> realCoordinates = object.getPlatformCoordinates()
                            .stream()
                            .map(c -> new Coordinate()
                                    .setX(object.getX() + c.getX())
                                    .setY(object.getY() + c.getY()))
                            .collect(Collectors.toList());

                    int walkerRadiusX = walker.getPlatformWidth() / 2;
                    int walkerRadiusY = walker.getPlatformHeight() / 2;

                    intersection = isCurvesIntersected(newX, newY, walkerRadiusX, walkerRadiusY, realCoordinates);
                }
            } else if (object.getPlatformShape() == PlatformShape.ELLIPSE) {
                if (walker.getPlatformShape() == PlatformShape.ELLIPSE) {

                    int walkerRadiusX = walker.getPlatformWidth() / 2;
                    int walkerRadiusY = walker.getPlatformHeight() / 2;
                    int objectRadiusX = object.getPlatformWidth() / 2;
                    int objectRadiusY = object.getPlatformHeight() / 2;

                    intersection = isCurvesIntersected(newX, newY, walkerRadiusX, walkerRadiusY,
                            object.getX(), object.getY(), objectRadiusX, objectRadiusY);
                }
            }
            if (intersection) {
                break;
            }
        }
        return !intersection;
    }

    public void makeStep(AbstractLiveObject player, int destX, int destY) {
        Coordinate coordinate = calcNextStep(player.getX(), player.getY(), destX, destY);
        if (isPassable(player, coordinate.getX(), coordinate.getY())) {
            player.setDirection(calcDirection(player.getX(), player.getY(), destX, destY));
            player.setX(coordinate.getX());
            player.setY(coordinate.getY());
            eventContext.addMapGoEvent(player.getUniqueName(), player.getX(), player.getY(), destX, destY);
        } else {
            eventContext.addMapGoImpossibleEvent(player.getUniqueName());
        }
    }

    public void enterPortal(Player player, LocationPortal portal) {
        player.getLocation().getObjects().remove(player);
        portal.getDestination().getObjects().add(player);
        player.setLocation(portal.getDestination());
        player.setX(portal.getDestX());
        player.setY(portal.getDestY());
    }
}
