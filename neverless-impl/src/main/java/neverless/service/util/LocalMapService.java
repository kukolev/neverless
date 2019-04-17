package neverless.service.util;

import neverless.PlatformShape;
import neverless.context.EventContext;
import neverless.domain.entity.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.domain.entity.mapobject.Player;
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
     * @param newX      new horizontal coordinate where walker wants to go to
     * @param newY      new vertical coordinate where walker wants to go to
     */
    public boolean isPassable(AbstractMapObject walker, int newX, int newY) {
        boolean intersection = false;

        for (AbstractMapObject object: walker.getLocation().getObjects()) {
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

                    int walkerCenterX = newX;
                    int walkerCenterY = newY;
                    int walkerRadiusX = walker.getPlatformWidth() / 2;
                    int walkerRadiusY = walker.getPlatformHeight() / 2;

                    intersection = isCurvesIntersected(walkerCenterX, walkerCenterY, walkerRadiusX, walkerRadiusY, realCoordinates);
                }
            } else if (object.getPlatformShape() == PlatformShape.ELLIPSE) {
                if (walker.getPlatformShape() == PlatformShape.ELLIPSE) {
                    int walkerCenterX = newX;
                    int walkerCenterY = newY;
                    int walkerRadiusX = walker.getPlatformWidth() / 2;
                    int walkerRadiusY = walker.getPlatformHeight() / 2;

                    int objectCenterX = object.getX();
                    int objectCenterY = object.getY();
                    int objectRadiusX = object.getPlatformWidth() / 2;
                    int objectRadiusY = object.getPlatformHeight() / 2;

                    intersection = isCurvesIntersected(walkerCenterX, walkerCenterY, walkerRadiusX, walkerRadiusY,
                            objectCenterX, objectCenterY, objectRadiusX, objectRadiusY);
                }
            }
            if (intersection) {
                break;
            }
        }
        return !intersection;
    }

    public void makeStep(Player player, int destX, int destY) {
        Coordinate coordinate = calcNextStep(player.getX(), player.getY(), destX, destY);
        if (isPassable(player, coordinate.getX(), coordinate.getY())) {
            player.setX(coordinate.getX());
            player.setY(coordinate.getY());
            player.setDirection(calcDirection(player.getX(), player.getY(), destX, destY));
            eventContext.addMapGoEvent(player.getUniqueName(), player.getX(), player.getY(), destX, destY);
        } else {
            eventContext.addMapGoImpossibleEvent(player.getUniqueName());
        }
    }
}
