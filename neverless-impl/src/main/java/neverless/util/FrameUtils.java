package neverless.util;

import neverless.domain.view.AreaHighlighted;
import neverless.domain.view.Sprite;

import java.util.List;

public class FrameUtils {

    /**
     * Returns sprite placed under given coordinates.
     *
     * @param sprites list of all sprites.
     * @param screenX horizontal coordinate.
     * @param screenY vertical coordinate.
     */
    public static Sprite getSpriteAtScreenCoordinates(List<Sprite> sprites, int screenX, int screenY) {
        return sprites.stream().
                filter(s -> screenX >= s.getX() - s.getWidth() / 2 &&
                        screenX <= s.getX() + s.getWidth() / 2 &&
                        screenY >= s.getY() - s.getHeight() &&
                        screenY <= s.getY())
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns true if the point is inner relative to area.
     *
     * @param areaHighlighted   area that should be checked.
     * @param screenX           horizontal coordinate of the point.
     * @param screenY           vertical coordinate of the point.
     * @return
     */
    public static boolean isAreaAtScreenCoordinates(AreaHighlighted areaHighlighted, int screenX, int screenY) {
        if (areaHighlighted != null) {
            return CoordinateUtils.isPointInner(screenX, screenY, areaHighlighted.getCoordinates());
        }
        return false;
    }
}
