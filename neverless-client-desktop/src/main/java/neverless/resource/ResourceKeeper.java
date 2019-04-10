package neverless.resource;

import neverless.Direction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static neverless.Direction.EAST;
import static neverless.Direction.NORTH;
import static neverless.Direction.NORTH_EAST;
import static neverless.Direction.NORTH_WEST;
import static neverless.Direction.SOUTH;
import static neverless.Direction.SOUTH_EAST;
import static neverless.Direction.SOUTH_WEST;
import static neverless.Direction.WEST;
import static neverless.Signatures.IMG_PLAYER;

@Component
public class ResourceKeeper {

    private static final Resource DEFAULT_RESOURCE = new Resource("no_resource.png", 0, 0, 32, 32);

    private Map<String, Resource> map = new HashMap<>();
    {
        map.put(IMG_PLAYER + "_" + SOUTH_WEST + "_01", new Resource("player_walking_diag.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_WEST + "_02", new Resource("player_walking_diag.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_WEST + "_03", new Resource("player_walking_diag.png", 256, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_WEST + "_04", new Resource("player_walking_diag.png", 384, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_WEST + "_05", new Resource("player_walking_diag.png", 512, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_WEST + "_06", new Resource("player_walking_diag.png", 640, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_WEST + "_07", new Resource("player_walking_diag.png", 768, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_WEST + "_08", new Resource("player_walking_diag.png", 896, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + NORTH_WEST + "_01", new Resource("player_walking_diag.png", 0, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_WEST + "_02", new Resource("player_walking_diag.png", 128, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_WEST + "_03", new Resource("player_walking_diag.png", 256, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_WEST + "_04", new Resource("player_walking_diag.png", 384, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_WEST + "_05", new Resource("player_walking_diag.png", 512, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_WEST + "_06", new Resource("player_walking_diag.png", 640, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_WEST + "_07", new Resource("player_walking_diag.png", 768, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_WEST + "_08", new Resource("player_walking_diag.png", 896, 128, 128, 128));

        map.put(IMG_PLAYER + "_" + SOUTH_EAST + "_01", new Resource("player_walking_diag.png", 0, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_EAST + "_02", new Resource("player_walking_diag.png", 128, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_EAST + "_03", new Resource("player_walking_diag.png", 256, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_EAST + "_04", new Resource("player_walking_diag.png", 384, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_EAST + "_05", new Resource("player_walking_diag.png", 512, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_EAST + "_06", new Resource("player_walking_diag.png", 640, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_EAST + "_07", new Resource("player_walking_diag.png", 768, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH_EAST + "_08", new Resource("player_walking_diag.png", 896, 256, 128, 128));

        map.put(IMG_PLAYER + "_" + NORTH_EAST + "_01", new Resource("player_walking_diag.png", 0, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_EAST + "_02", new Resource("player_walking_diag.png", 128, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_EAST + "_03", new Resource("player_walking_diag.png", 256, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_EAST + "_04", new Resource("player_walking_diag.png", 384, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_EAST + "_05", new Resource("player_walking_diag.png", 512, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_EAST + "_06", new Resource("player_walking_diag.png", 640, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_EAST + "_07", new Resource("player_walking_diag.png", 768, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH_EAST + "_08", new Resource("player_walking_diag.png", 896, 384, 128, 128));

        map.put(IMG_PLAYER + "_" + SOUTH + "_01", new Resource("player_walking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH + "_02", new Resource("player_walking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH + "_03", new Resource("player_walking.png", 256, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH + "_04", new Resource("player_walking.png", 384, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH + "_05", new Resource("player_walking.png", 512, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH + "_06", new Resource("player_walking.png", 640, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH + "_07", new Resource("player_walking.png", 768, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + SOUTH + "_08", new Resource("player_walking.png", 896, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + WEST + "_01", new Resource("player_walking.png", 0, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + WEST + "_02", new Resource("player_walking.png", 128, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + WEST + "_03", new Resource("player_walking.png", 256, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + WEST + "_04", new Resource("player_walking.png", 384, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + WEST + "_05", new Resource("player_walking.png", 512, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + WEST + "_06", new Resource("player_walking.png", 640, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + WEST + "_07", new Resource("player_walking.png", 768, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + WEST + "_08", new Resource("player_walking.png", 896, 128, 128, 128));

        map.put(IMG_PLAYER + "_" + EAST + "_01", new Resource("player_walking.png", 0, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + EAST + "_02", new Resource("player_walking.png", 128, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + EAST + "_03", new Resource("player_walking.png", 256, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + EAST + "_04", new Resource("player_walking.png", 384, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + EAST + "_05", new Resource("player_walking.png", 512, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + EAST + "_06", new Resource("player_walking.png", 640, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + EAST + "_07", new Resource("player_walking.png", 768, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + EAST + "_08", new Resource("player_walking.png", 896, 256, 128, 128));

        map.put(IMG_PLAYER + "_" + NORTH + "_01", new Resource("player_walking.png", 0, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH + "_02", new Resource("player_walking.png", 128, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH + "_03", new Resource("player_walking.png", 256, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH + "_04", new Resource("player_walking.png", 384, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH + "_05", new Resource("player_walking.png", 512, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH + "_06", new Resource("player_walking.png", 640, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH + "_07", new Resource("player_walking.png", 768, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + NORTH + "_08", new Resource("player_walking.png", 896, 384, 128, 128));
    }

    /**
     * Returns true if resource exists in map.
     *
     * @param signature     object signature.
     * @param direction     direction determines orientation of object.
     * @param imageNumber   number of frame.
     */
    public boolean isResourceExists(String signature, Direction direction, int imageNumber) {
        String key = calcKey(signature, direction, imageNumber);
        return map.containsKey(key);
    }

    /**
     * Returns resource for object.
     *
     * @param signature     object signature.
     * @param direction     direction determines orientation of object.
     * @param imageNumber   number of frame.
     */
    public Resource getResource(String signature, Direction direction, int imageNumber) {
        String key = calcKey(signature, direction, imageNumber);
        Resource result = map.get(key);
        if (result == null) {
            result = getDefault();
        }
        return result;
    }

    /**
     * Returns default resource.
     */
    public Resource getDefault() {
        return DEFAULT_RESOURCE;
    }

    /**
     * Calculates and returns key for map.
     *
     * @param signature     object signature.
     * @param direction     direction determines orientation of object.
     * @param imageNumber   number of frame.
     */
    private String calcKey(String signature, Direction direction, int imageNumber) {
        String numb = String.valueOf(imageNumber);
        if (imageNumber < 10) {
            numb = "0" + numb;
        }
        return signature + "_" + direction + "_" + numb;
    }
}