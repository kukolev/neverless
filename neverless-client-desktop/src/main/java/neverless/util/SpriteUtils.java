package neverless.util;

import neverless.view.domain.Sprite;
import java.util.List;

public class SpriteUtils {

    /**
     * Returns sprite placed under given coordinates.
     *
     * @param sprites   list of all sprites.
     * @param screenX   horizontal coordinate.
     * @param screenY   vertical coordinate.
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
}
