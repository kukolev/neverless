package neverless.context;

import lombok.Getter;
import lombok.Setter;
import neverless.domain.view.DestinationMarkerData;
import neverless.domain.Coordinate;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Container for all objects needed for display on screen.
 */
public class ViewContext {
    /** Screen horizontal coordinate */
    private int screenX;

    /** Screen vertical coordinate */
    private int screenY;

    @Getter
    @Setter
    private DestinationMarkerData marker;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * Sets both screen coordinates.
     *
     * @param screenX   screen horizontal coordinate.
     * @param screenY   screen vertical coordinate.
     */
    public void setScreenPoint(int screenX, int screenY) {
        lock.lock();
        try {
            this.screenX = screenX;
            this.screenY = screenY;
        } finally {
            lock.unlock();
        }
    }

    public Coordinate getScreenPoint() {
        lock.lock();
        try {
            Coordinate result = new Coordinate()
                    .setX(screenX)
                    .setY(screenY);
            return result;
        } finally {
            lock.unlock();
        }
    }
}
