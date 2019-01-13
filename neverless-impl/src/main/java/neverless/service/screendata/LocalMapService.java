package neverless.service.screendata;

import neverless.PlatformShape;
import neverless.domain.entity.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Coordinate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static neverless.util.CoordinateUtils.isCurvesIntersected;

@Service
public class LocalMapService {

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

                    int walkerCenterX = newX + walker.getPlatformCenterX();
                    int walkerCenterY = newY + walker.getPlatformCenterY();
                    int walkerRadiusX = walker.getPlatformWidth() / 2;
                    int walkerRadiusY = walker.getPlatformHeight() / 2;

                    intersection = isCurvesIntersected(walkerCenterX, walkerCenterY, walkerRadiusX, walkerRadiusY, realCoordinates);
                }
            } else if (object.getPlatformShape() == PlatformShape.ELLIPSE) {
                if (walker.getPlatformShape() == PlatformShape.ELLIPSE) {
                    int walkerCenterX = newX + walker.getPlatformCenterX();
                    int walkerCenterY = newY + walker.getPlatformCenterY();
                    int walkerRadiusX = walker.getPlatformWidth() / 2;
                    int walkerRadiusY = walker.getPlatformHeight() / 2;

                    int objectCenterX = object.getX() + object.getPlatformCenterX();
                    int objectCenterY = object.getY() + object.getPlatformCenterY();
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

    public boolean isPortal(int x, int y, Location location) {
        // todo: fix it.
       return false;
    }
}
