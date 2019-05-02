package neverless.service.model.util;

import neverless.util.PlatformShape;
import neverless.context.EventContext;
import neverless.domain.model.entity.mapobject.AbstractLiveObject;
import neverless.domain.model.entity.mapobject.AbstractPhysicalObject;
import neverless.util.Coordinate;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.portal.LocationPortal;
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

    /**
     * Returns true if walker can go to new coordinates.
     *
     * @param walker    walker.
     * @param newX      new horizontal coordinate where walker wants to go to.
     * @param newY      new vertical coordinate where walker wants to go to.
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

    /**
     * Performs moving step to destination coordinates.
     *
     * @param walker    walker.
     * @param destX     horizontal destination coordinate where walker wants to go to.
     * @param destY     vertical destination coordinate where walker wants to go to.
     */
    public void makeStep(AbstractLiveObject walker, int destX, int destY) {
        Coordinate coordinate = calcNextStep(walker.getX(), walker.getY(), destX, destY);
        if (isPassable(walker, coordinate.getX(), coordinate.getY())) {
            walker.setDirection(calcDirection(walker.getX(), walker.getY(), destX, destY));
            walker.setX(coordinate.getX());
            walker.setY(coordinate.getY());
            eventContext.addMapGoEvent(walker.getUniqueName(), walker.getX(), walker.getY(), destX, destY);
        } else {
            eventContext.addMapGoImpossibleEvent(walker.getUniqueName());
        }
    }

    /**
     * Performs player portal entering.
     *
     * @param player    player
     * @param portal    portal which player enters.
     */
    public void enterPortal(Player player, LocationPortal portal) {
        player.getLocation().getObjects().remove(player);
        portal.getDestination().getObjects().add(player);
        player.setLocation(portal.getDestination());
        player.setX(portal.getDestX());
        player.setY(portal.getDestY());
    }
}
